package com.T82.ticket.utils.grpc;

import com.T82.common_exception.exception.coupon.NotFoundRemainingCouponException;
import com.T82.ticket.api.ApiFeign;
import com.T82.ticket.dto.request.TicketRequestDto;
import com.T82.ticket.dto.response.QRCodeResponseDto;
import com.T82.ticket.global.domain.entity.Ticket;
import com.T82.ticket.global.domain.repository.TicketRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.t82.event.lib.EventGrpc;
import org.t82.event.lib.GetEventReply;
import org.t82.event.lib.GetEventRequest;
import org.t82.seat.lib.SeatDetailRequest;
import org.t82.seat.lib.SeatDetailResponse;
import org.t82.seat.lib.SeatGrpc;

@Service
@Slf4j
@RequiredArgsConstructor
public class GrpcClientService {
    private final TicketRepository ticketRepository;
    private final ApiFeign apiFeign;

    @GrpcClient("event")
    private EventGrpc.EventBlockingStub eventStub;

    @GrpcClient("seat")
    private SeatGrpc.SeatStub seatStub;

    @KafkaListener(topics = "paymentSuccess", groupId = "paySuccess-group")
    public void saveTickets(TicketRequestDto req) {
        log.info("paymentSuccess = {}", req.toString());
        long start = System.currentTimeMillis();
        GetEventReply eventReply = eventStub.getEventDetail(
                GetEventRequest
                        .newBuilder()
                        .setEventId(req.eventId())
                        .build()
        );
        log.info("이벤트 통신 시간 : {}", (System.currentTimeMillis() - start));
        // 좌석 ID 목록 생성
        SeatDetailRequest.Builder builder = SeatDetailRequest.newBuilder();
        req.items().forEach(item -> builder.addSeatId(item.seatId()));
        // StreamObserver 생성
        StreamObserver<SeatDetailResponse> responseObserver = new StreamObserver<SeatDetailResponse>() {
            @Override
            public void onNext(SeatDetailResponse reply) {
                req.items()
                        .stream()
                        .filter(item -> item.seatId() == reply.getId())
                        .forEach(item -> {
                            try {
                                QRCodeResponseDto qrResponse = apiFeign.uploadQRCode(String.valueOf(item.seatId()));
                                ticketRepository.save(Ticket.toEntity(req, eventReply, reply, item.amount(), qrResponse.fileUrl()));
                            } catch (RuntimeException e) {
                                throw new NotFoundRemainingCouponException();
                            }
                        });
            }

            @Override
            public void onError(Throwable t) {
                // 에러 발생 시 처리
                System.err.println("Error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                log.info("paymentSuccess 시간 소요 = {}", (System.currentTimeMillis() - start));
                // 서버에서 모든 스트림 메시지를 전송한 후 처리
                System.out.println("Stream completed.   ");
            }
        };
        // 좌석 정보 가져와서 저장하기
        seatStub.getSeatDetail(builder.build(), responseObserver);
    }
}
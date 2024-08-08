package com.T82.ticket.utils.grpc;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.t82.event.lib.EventGrpc;
import org.t82.event.lib.GetEventReply;
import org.t82.event.lib.GetEventRequest;

@Service
@RequiredArgsConstructor
public class GrpcClientService {
    private GrpcUtil grpcUtil;

    @GrpcClient("event")
    private EventGrpc.EventBlockingStub eventStub;

    public GetEventReply getEventInfo(Long eventId) {
        return eventStub.getEventDetail(GetEventRequest.newBuilder().setEventId(eventId).build());
    }
}

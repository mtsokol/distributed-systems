import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import protos.ExchangeGrpc;
import protos.ExchangeProto;

import java.io.IOException;

public class Main {

    Server server;

    private void start() throws IOException {
        int port = 5000;
        server = ServerBuilder.forPort(port)
                .addService(new Service())
                .build()
                .start();
       // Runtime.getRuntime()
    }

    public static void main(String[] args) throws IOException, InterruptedException {

    }

    private static class Service extends ExchangeGrpc.ExchangeImplBase {
        @Override
        public void subscribeExchangeRate(ExchangeProto.ExchangeRequest request, StreamObserver<ExchangeProto.ExchangeStream> responseObserver) {

            ExchangeProto.Currency xd = request.getOriginCurrency();

            request.getCurrencyRatesList();

            while (true) {

                ExchangeProto.ExchangeStream x = ExchangeProto.ExchangeStream.newBuilder()
                        .setCurrency(ExchangeProto.Currency.CHF).setExchangeRate(23.3).build();

                responseObserver.onNext(x);

            }


            //responseObserver.onCompleted();
        }
    }
}

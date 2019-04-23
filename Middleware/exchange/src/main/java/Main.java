import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import protos.ExchangeGrpc;
import protos.ExchangeProto;

import java.io.IOException;

public class Main {

    Server server;

    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new Service())
                .build()
                .start();
       // Runtime.getRuntime()
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        final Main main = new Main();
        main.start();
        main.blockUntilShutdown();
    }

    private static class Service extends ExchangeGrpc.ExchangeImplBase {
        @Override
        public void subscribeExchangeRate(ExchangeProto.ExchangeRequest request, StreamObserver<ExchangeProto.ExchangeStream> responseObserver) {

            ExchangeProto.Currency xd = request.getOriginCurrency();

            request.getCurrencyRatesList();

            int i = 0;

            while (i < 5) {

                i++;

                ExchangeProto.ExchangeStream x = ExchangeProto.ExchangeStream.newBuilder()
                        .setCurrency(ExchangeProto.Currency.CHF).setExchangeRate(23.3).build();

                responseObserver.onNext(x);

            }


            responseObserver.onCompleted();
        }
    }
}

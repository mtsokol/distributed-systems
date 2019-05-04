import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import protos.ExchangeGrpc;
import protos.ExchangeProto;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Main {

    private Server server;

    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new Service())
                .build()
                .start();
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
        public void subscribeExchangeRate(ExchangeProto.ExchangeRequest request,
                                          StreamObserver<ExchangeProto.ExchangeStream> responseObserver) {

            Random generator = new Random();
            ExchangeProto.Currency originCurrency = request.getOriginCurrency();

            List<protos.ExchangeProto.Currency> ratesList = request.getCurrencyRatesList();
            ratesList.remove(originCurrency);

            while (true) {

                try {
                    int sleepTime = generator.nextInt(500) + 500;
                    Thread.sleep(sleepTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (ExchangeProto.Currency currency : ratesList) {
                    int value = generator.nextInt(20) + 1;
                    ExchangeProto.ExchangeStream rate = ExchangeProto.ExchangeStream.newBuilder()
                            .setCurrency(currency).setExchangeRate(value).build();

                    responseObserver.onNext(rate);
                }
            }
        }
    }
}

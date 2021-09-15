package guru.qa;

import com.google.protobuf.util.JsonFormat;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.Test;
import profile.ProfileGrpc;
import profile.ProfileOuterClass;

import static com.google.protobuf.util.JsonFormat.printer;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileTest {

    private JsonFormat.Printer jsonPrinter = printer();
    private ProfileGrpc.ProfileBlockingStub profileBlockingStub = ProfileGrpc.newBlockingStub(
            ManagedChannelBuilder.forAddress("localhost", 8092)
                    .usePlaintext()
                    .build()
    );

    @Test
    void profileTest() throws Exception {
        ProfileOuterClass.Request request = ProfileOuterClass.Request.newBuilder()
                .addHotelIds("4")
                .setLocale("US")
                .build();

        System.out.println("Request: " + jsonPrinter.print(request));
        ProfileOuterClass.Result response = profileBlockingStub.getProfiles(request);
        assertEquals("Hotel Vitale", response.getHotels(0).getName());
        System.out.println("response: " + jsonPrinter.print(response));
    }
}

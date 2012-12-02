import java.io.IOException;

import javax.xml.ws.Endpoint;

import assignmentImplementation.IndexImpl;
import assignmentImplementation.KeyValueBaseService;


public class Main {

    public static void main(String[] args) throws IndexOutOfBoundsException, IOException {
        IndexImpl idx = new IndexImpl();

        Endpoint.publish("http://localhost:8080/kv",
                new KeyValueBaseService(idx));

    }

}

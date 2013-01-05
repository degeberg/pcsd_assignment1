package keyValueBaseInterfaces;

/**
 * This class is intended to keep the WSDL
 * file locations of all the internal RPC
 * services. Those locations are used by the
 * proxies and the master to find all the
 * replicas.
 */
public class Configuration {
	public String master = "http://localhost:8080/keyvalue/kvmaster?wsdl";
	public String[] slaves = { "http://localhost:8080/keyvalue/kvslave1?wsdl" };
}

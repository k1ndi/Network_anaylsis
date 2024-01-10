/*
    * Name: Abdulrahman AL Kindi
    * Banner ID: B00858783
 */

public class HostPortPair {

    private char portNum;
    private String hostAddress;

    public HostPortPair (String hostAddress ,char portNum ){
        this.hostAddress= hostAddress;
        this.portNum= portNum;
    }

    public char getPortNum() {
        return portNum;
    }

    public void setPortNum(char portNum) {
        this.portNum = portNum;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }
}

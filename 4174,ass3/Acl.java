public class Acl {

    private int aclNumber;
    private boolean decision;
    private String sourceIp;
    private String destinationIp;
    private String sourceMask;
    private String destinationMask;
    private String port;

    public Acl(int aclNumber, boolean decision, String sourceIp,  String sourceMask) {
        this.aclNumber = aclNumber;
        this.decision = decision;
        this.sourceIp = sourceIp;
        this.sourceMask = sourceMask;
        this.destinationIp="NA";
        this.destinationMask= "standard";
    }

    public int getAclNumber() {
        return aclNumber;
    }

    public void setAclNumber(int aclNumber) {
        this.aclNumber = aclNumber;
    }

    public boolean isDecision() {
        return decision;
    }

    public void setDecision(boolean decision) {
        this.decision = decision;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getDestinationIp() {
        return destinationIp;
    }

    public void setDestinationIp(String destinationIp) {
        this.destinationIp = destinationIp;
    }

    public String getSourceMask() {
        return sourceMask;
    }

    public void setSourceMask(String sourceMask) {
        this.sourceMask = sourceMask;
    }

    public String getDestinationMask() {
        return destinationMask;
    }

    public void setDestinationMask(String destinationMask) {
        this.destinationMask = destinationMask;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}

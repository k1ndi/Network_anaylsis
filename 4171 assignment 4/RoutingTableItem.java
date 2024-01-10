/*
 * Name: Abdulrahman Al Kindi
 * Banner ID: B00858783
 */

public class RoutingTableItem {
    private int mask;
    private String Ip;
    private String nextHop;
    private String outGoingItf;

    public RoutingTableItem (String Ip, String nextHop, String outGoingItf, int mask)
    {
        this.Ip= Ip;
        this.nextHop= nextHop;
        this.outGoingItf= outGoingItf;
        this.mask= mask;
    }

    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public String getNextHop() {
        return nextHop;
    }

    public void setNextHop(String nextHop) {
        this.nextHop = nextHop;
    }

    public String getOutGoingItf() {
        return outGoingItf;
    }

    public void setOutGoingItf(String outGoingItf) {
        this.outGoingItf = outGoingItf;
    }

    @Override
    public String toString() {
        if (nextHop.equals("-")){
            return " will be forwarded on the directly connected network on interface " + outGoingItf;
        }
        else {

            return " will be forwarded to "+ nextHop+ " out on interface "+ outGoingItf;
        }
    }
}

public class Routing{
    public String profile;
    public int cost_per_second;
    public int cost_per_meter;

    public int getCost_per_meter() {
        return cost_per_meter;
    }

    public int getCost_per_second() {
        return cost_per_second;
    }

    public String getProfile() {
        return profile;
    }

    public void setCost_per_meter(int cost_per_meter) {
        this.cost_per_meter = cost_per_meter;
    }

    public void setCost_per_second(int cost_per_second) {
        this.cost_per_second = cost_per_second;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}


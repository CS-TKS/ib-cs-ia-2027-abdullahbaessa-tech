package app;

public class Session {
    private int sessionId;
    private int playerId;
    private String date;
    private double bmi;

    public Session(int playerId, String date, double bmi) {
        this.playerId = playerId;
        this.date = date;
        this.bmi = bmi;
    }

    public Session(int sessionId, int playerId, String date, double bmi) {
        this.sessionId = sessionId;
        this.playerId = playerId;
        this.date = date;
        this.bmi = bmi;
    }

    public int getSessionId() { return sessionId; }
    public int getPlayerId() { return playerId; }
    public String getDate() { return date; }
    public double getBmi() { return bmi; }
}

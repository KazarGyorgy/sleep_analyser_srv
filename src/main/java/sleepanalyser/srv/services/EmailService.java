package sleepanalyser.srv.services;

public interface EmailService {

    void sendLoginCredentials(String to, String subject, String body);
}

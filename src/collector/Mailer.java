package collector;

import java.util.function.Consumer;

/* Creating fluent interfaces with lambda expression */
class PrivateMailer {

    private PrivateMailer() {

    }

    public PrivateMailer from(String address) {
        System.out.println("from");
        return this;
    }

    public PrivateMailer to(String address) {
        System.out.println("to");
        return this;
    }

    public PrivateMailer subject(String subject) {
        System.out.println("Subject");
        return this;
    }

    public PrivateMailer body(String body) {
        System.out.println("Body");
        return this;
    }

    public static void send(Consumer<PrivateMailer> block) {
        PrivateMailer mailer = new PrivateMailer();
        block.accept(mailer);
        System.out.println("... Sending ...");
    }
}

class NewMailer {
    public NewMailer from(String address) {
        System.out.println("from");
        return this;
    }

    public NewMailer to(String address) {
        System.out.println("to");
        return this;
    }

    public NewMailer subject(String subject) {
        System.out.println("Subject");
        return this;
    }

    public NewMailer body(String body) {
        System.out.println("Body");
        return this;
    }

    public NewMailer send() {
        System.out.println("... Sending ...");
        return this;
    }
}

public class Mailer {

    public void from(String address) {
        System.out.println("from");
    }

    public void to(String address) {
        System.out.println("to");
    }

    public void subject(String subject) {
        System.out.println("Subject");
    }

    public void body(String body) {
        System.out.println("Body");
    }

    public void send() {
        System.out.println("... Sending ...");
    }

    public static void main(String[] args) {
        Mailer mailer = new Mailer();
        mailer.from("test.mail.com");
        mailer.to("dest.mail.com");
        mailer.subject("Hello");
        mailer.body("How are you ?");
        mailer.send();
        //This code is very noisy

        NewMailer newMailer = new NewMailer();
        newMailer.from("test.mail.com")
            .to("dest.mail.com")
            .subject("Hello")
            .body("How are you ?")
            .send();
        // This is called cascade method pattern
        // We can further reduce the noise. Lets make the constructor private


        PrivateMailer.send(mailer1 -> mailer1
                        .from("test.mail.com")
                        .to("dest.mail.com")
                        .subject("Hello")
                        .body("How are you ?"));
        // Dealing with object allocation. You have taken cascade method pattern and got rid of creating Mailer object
        // User of the code does not have to worry about object allocation


    }

}

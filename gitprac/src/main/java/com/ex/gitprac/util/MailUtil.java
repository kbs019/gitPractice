// package com.ex.gitprac.util;

// import java.util.Properties;

// import jakarta.mail.Authenticator;
// import jakarta.mail.Message;
// import jakarta.mail.PasswordAuthentication;
// import jakarta.mail.Session;
// import jakarta.mail.Transport;
// import jakarta.mail.internet.InternetAddress;
// import jakarta.mail.internet.MimeMessage;

// public class MailUtil {
//     public static void sendTempPw(String toEmail, String tempPw) {
//         try {
//             Properties props = new Properties();
//             props.put("mail.smtp.host", "smtp.gmail.com");
//             props.put("mail.smtp.port", "587");
//             props.put("mail.smtp.auth", "true");
//             props.put("mail.smtp.starttls.enable", "true");

//             String fromEmail = "hongtest1998@gmail.com";
//             String password = "hrnv hzwo xrhv ecdp";

//             Session session = Session.getInstance(props, new Authenticator() {
//                 protected PasswordAuthentication getPasswordAuthentication() {
//                     return new PasswordAuthentication(fromEmail, password);
//                 }
//             });

//             Message message = new MimeMessage(session);
//             message.setFrom(new InternetAddress(fromEmail));
//             message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
//             message.setSubject("하루 견과 임시 비밀번호 발급 안내");
//             message.setText("임시 비밀번호: " + tempPw + "\n로그인 후 비밀번호를 변경해주세요.");

//             Transport.send(message); // ✅ 요기!
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }

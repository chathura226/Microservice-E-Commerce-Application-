package com.chathuralakshan.notification.notification;

import com.chathuralakshan.notification.kafka.payment.PaymentConfirmation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification,String> {

}

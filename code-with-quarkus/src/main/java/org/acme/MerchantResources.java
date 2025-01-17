package org.acme;
import java.util.List;
import org.acme.models.Merchant;
import org.acme.services.MerchantService;
import dtu.ws.fastmoney.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


import com.rabbitmq.client.Channel;
import com.fasterxml.jackson.databind.ObjectMapper;


@Path("/merchant")
public class MerchantResources{
    MerchantService service = new MerchantService();
    public record UserAccountId(User user, String accountId) {}
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Merchant> customer() {
        return service.getMerchants();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void setCustomerBank(UserAccountId userAccountId){
        User user = userAccountId.user;
        String accountId = userAccountId.accountId;
        service.setMerchant(user,accountId);
        try {
            // Convert UserAccountId to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String message = objectMapper.writeValueAsString(userAccountId);
    
            // Get RabbitMQ channel
            Channel channel = RabbitMQUtil.getChannel();
    
            // Declare the queue
            channel.queueDeclare(RabbitMQUtil.getQueueName(), true, false, false, null);
    
            // Publish the message to the queue
            channel.basicPublish("", RabbitMQUtil.getQueueName(), null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
    
            // Close the channel
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add customer to RabbitMQ", e);
        }
    }
}

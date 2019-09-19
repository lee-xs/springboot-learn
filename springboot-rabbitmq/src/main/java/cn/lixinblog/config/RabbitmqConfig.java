package cn.lixinblog.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * rabbitmq 配置类
 */
@Configuration
public class RabbitmqConfig {

    private static Logger log = LoggerFactory.getLogger(RabbitmqConfig.class);

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Bean
    public Queue helloQueue(){
        return new Queue("helloQueue");
    }

    @Bean
    public Queue userQueue(){
        return new Queue("userQueue");
    }

    /** =============== 以下是验证direct Exchange的队列 =============== */

    @Bean
    public Queue directQueue(){
        return new Queue("directQueue");
    }

    /** =============== 以下是验证topic Exchange的队列 =============== */

    @Bean
    public Queue queueMessage(){
        return new Queue("topicMessage");
    }

    @Bean
    public Queue queueMessages(){
        return new Queue("topicMessages");
    }

    /** =============== 以下是验证Fanout Exchange的队列 =============== */

    @Bean(name = "oneMessage")
    public Queue oneMessage(){
        return new Queue("fanout.one");
    }

    @Bean(name = "twoMessage")
    public Queue twoMessage(){
        return new Queue("fanout.two");
    }

    @Bean(name = "threeMessage")
    public Queue threeMessage(){
        return new Queue("fanout.three");
    }

    /**
     * exchange是交换机，交换机的主要作用是接收相应的消息并且绑定到指定的队列，交换机的类型有四种，分别为Direct,topic,headers,Fanout
     *
     * Dirdct是RabbitMQ默认的交换机模式，也是最简单的方式，即创建消息队列的时候，指定一个BindingKey。当发送者发送消息的时候，指定对应的Key，当Key和消息队列的
     * BindingKey一致的时候，消息将会被发送到该消息队列中
     *
     * topic转告消息主要是依据通配符，队列和交换机的绑定主要是依据一种模式（通配符+字符串），而当发送消息的时候，只有指定的Key和该模式相匹配的时候，消息才会被发送到该消息队列中
     *
     * headers也是根据一个规则进行匹配，在消息队列和交换机绑定的时候会指定一组键值对规则，而发送消息的时候也会指定一组键值对规则，当两组键值对规则相匹配的时候，消息会被发送到
     * 匹配的消息队列中
     *
     * Fanout是路由广播的形式，将会把消息发给绑定它的全部队列，即便设置了Key，也会被忽略
     */
    @Bean
    DirectExchange directExchange(){
        return new DirectExchange("directExchange");
    }

    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange("topicExchange"); // 参数1为交换机的名称
    }

    /**
     * //配置广播路由器
     * @return FanoutExchange
     */
    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutExchange"); // 参数1为交换机的名称
    }

    /**
     * 将队列directQueue与 directExchange交换机绑定，routing_key为direct
     * @param directQueue
     * @param directExchange
     * @return
     */
    @Bean
    Binding bindingExchangeDirect(@Qualifier("directQueue") Queue directQueue, DirectExchange directExchange){
        return BindingBuilder.bind(directQueue).to(directExchange).with("direct");
    }

    @Bean
    Binding bindingExchangeMessage(@Qualifier("queueMessage") Queue queueMessage, TopicExchange topicExchange){
        return BindingBuilder.bind(queueMessage).to(topicExchange).with("topic.msg");
    }

    /**
     * 将队列topic.messages与exchange绑定，routing_key为topic.#,模糊匹配
     * @param queueMessages
     * @param topicExchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessages(@Qualifier("queueMessages") Queue queueMessages, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueMessages).to(topicExchange).with("topic.#");
    }

    @Bean
    Binding bindingExchangeOne(@Qualifier("oneMessage") Queue oneMessage,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(oneMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeTwo(@Qualifier("twoMessage") Queue twoMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(twoMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeThree(@Qualifier("threeMessage") Queue threeMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(threeMessage).to(fanoutExchange);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        /**
         * 若使用confirm-callback或return-callback，必须要配置publisherConfirms或publisherReturns为true
         * 每个rabbitTemplate只能有一个confirm-callback和return-callback，如果这里配置了，那么写生产者的时候
         * 不能再写confirm-callback和return-callback
         * 使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true
         */
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        /**
         * 如果消息没有到exchange,则confirm回调，ack=false
         * 如果消息到达exchange,则confirm回调,ack=true
         * exchange到queue成功,则不回调return
         * exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if(ack){
                log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
            }else{
                log.info("消息发送失败:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
            }
        });
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
        });
        /*rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if(ack){
                    log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
                }else{
                    log.info("消息发送失败:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
                }
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
            }
        });*/
        return rabbitTemplate;
    }


}
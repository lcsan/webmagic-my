package org.webmagic.xml;

import redis.clients.jedis.JedisPubSub;

public class NewsListener extends JedisPubSub {
    @Override
    public void onMessage(String channel, String message) {
        System.out.println("get message from" + channel + "   " + message);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println("get message from" + channel + "   " + message);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println("subscribe the channel:" + channel);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println("get message from" + channel);
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        System.out.println("get message from" + subscribedChannels);
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("get message from" + subscribedChannels);
    }
}

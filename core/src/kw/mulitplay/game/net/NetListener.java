package kw.mulitplay.game.net;

import kw.mulitplay.game.Message;

public interface NetListener {
    public Message action(Message message);
}

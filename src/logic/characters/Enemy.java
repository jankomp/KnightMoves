package logic.characters;

import logic.Coin;

public interface Enemy {
public Coin coin = new Coin();
public int getLeftPoint();
public int getHighestPoint();
public int getLowestPoint();
public int getRightPoint();
public void hurt();
public void pickUpCoin();
}

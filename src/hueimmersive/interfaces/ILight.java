package hueimmersive.interfaces;

import java.awt.Color;


public interface ILight
{
	String getName();
	String getUniqueID();

	boolean isOn() throws Exception;
	void setOn(boolean on) throws Exception;

	boolean getActive() throws Exception;
	void setActive(boolean active) throws Exception;

	float getBrightnessMultiplier() throws Exception;
	void setBrightnessMultiplier(float multiplier) throws Exception;

	int getAlgorithm() throws Exception;
	void setAlgorithm(int algorithm) throws Exception;

    void setColor(Color color) throws Exception;
    Color getColor() throws Exception;

    void storeColor() throws Exception;
    void restoreColor() throws Exception;
}

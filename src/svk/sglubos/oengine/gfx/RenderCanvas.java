package svk.sglubos.oengine.gfx;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import svk.sglubos.oengine.utils.debug.DebugStringBuilder;
import svk.sglubos.oengine.utils.debug.MessageHandler;

@SuppressWarnings("serial")
public class RenderCanvas extends Canvas {
	protected BufferedImage renderLayer;
	protected double scale = 1.0;
	protected BufferStrategy bs;

	public RenderCanvas(Screen screen,double scale){
		renderLayer = screen.getRenderLayer();
		setPreferredSize(new Dimension((int)((screen.getWidth() + 10) * scale), (int)((screen.getHeight() + 10) * scale)));
		
		this.scale = scale;
	}
	
	public void init(int numBuffers){
		try{
			createBufferStrategy(numBuffers);			
		}catch(Exception e){
			MessageHandler.printMessage("RENDER_CANVAS", MessageHandler.ERROR, "Exception while creating BufferStrategy ! printing stack trace\n");
			e.printStackTrace();
		}
		
		bs = getBufferStrategy();
	}
	
	//TODO Exception
	public void showRenderedContent(){
		if(bs == null){
			MessageHandler.printMessage("RENDER_CANVAS", MessageHandler.ERROR, "BufferStrategy is not initialized !");
			return;
		}
		
		Graphics g = null;
		
		do {
		    try{
		    	g = bs.getDrawGraphics();
		    	g.drawImage(renderLayer, 0, 0, getWidth(),getHeight(), null);
		    } finally {
		    	if(g != null)
		    		g.dispose();
		    }
		    
		    bs.show();
		} while (bs.contentsLost());
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.append(getClass(), hashCode());
		ret.increaseLayer();
		ret.appendln(super.toString());
		ret.append("scale", scale);
		ret.append(renderLayer, "renderlayer");
		ret.append(bs, "bs");
		ret.decreaseLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}

package svk.lubsar.j2dgl.utils.timer;

public class LoopTimerTask extends TimerTask {
	public static final int INFINITE = -1;
	private int cycles;
	
	public LoopTimerTask(byte timeFormat, double delay, int cycles, TimerCallback callback) {
		super(timeFormat, delay, callback);
		this.nextCallback = delay;
		this.cycles = cycles;
	}

	@Override
	public void update(double time) {
		nextCallback -= time;
		if(nextCallback <= 0) {
			callback.callback();
			
			if(cycles == INFINITE) {
				nextCallback = delay;
				return;
			} else if (cycles == 0) {
				done = true;
			} else {
				cycles--;
				nextCallback = delay;
			}
		}
	}
}

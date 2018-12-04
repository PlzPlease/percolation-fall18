import java.util.LinkedList;
import java.util.Queue;

public class PercolationBFS extends PercolationDFSFast {

	public PercolationBFS(int n) {
		super(n);
	}

	@Override
	protected void dfs(int row, int col) {
		// out of bounds?
		if (! inBounds(row,col)) return;
		
		// full or NOT open, don't process
		if (isFull(row, col) || !isOpen(row, col))
			return;
		
		Queue<Integer> qu = new LinkedList<>();
		int size = myGrid.length;
		myGrid[row][col] = FULL;
		qu.add(row*size+col);
		
		while (qu.size() > 0){
			int v = qu.remove();
			if (inBounds((v/size)-1,v%size) && isOpen((v/size)-1,v%size) && !isFull((v/size)-1,v%size)) {
				myGrid[(v/size)-1][v%size] = FULL;
				qu.add(((v/size)-1)*size+v%size);
			}
			if (inBounds((v/size)+1,v%size) && isOpen((v/size)+1,v%size)&& !isFull((v/size)+1,v%size)) {
				myGrid[(v/size)+1][v%size] = FULL;
				qu.add(((v/size)+1)*size+v%size);
			}
			if (inBounds((v/size),v%size+1) && isOpen((v/size),v%size+1)&& !isFull((v/size),v%size+1)) {
				myGrid[(v/size)][v%size+1] = FULL;
				qu.add(((v/size))*size+v%size+1);
			}
			if (inBounds((v/size),v%size-1) && isOpen((v/size),v%size-1) && !isFull((v/size),v%size-1)) {
				myGrid[(v/size)][v%size-1] = FULL;
				qu.add(((v/size))*size+v%size-1);
			}
		}
	}
}

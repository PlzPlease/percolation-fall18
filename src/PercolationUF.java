
public class PercolationUF implements IPercolate {
	boolean[][] myGrid;
	int myOpenCount = 0;
	IUnionFind myFinder;
	private int VTOP;
	private int VBOTTOM;

	public PercolationUF(int size, IUnionFind finder) {
		myGrid = new boolean[size][size];
		for (int row = 0 ; row<size; row+=1) {
			for (int col= 0 ; col<size; col+=1) {
				myGrid[row][col] = false;
			}
		}
		myFinder = finder;
		myFinder.initialize(size*size+2);
		VTOP = size*size;
		VBOTTOM = size*size+1;
	}

	@Override
	public void open(int row, int col) {
		myOpenCount += 1;
		if (!inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		myGrid[row][col] = true;
		int encode = row*myGrid.length+col;
		if(row==0) myFinder.union(encode, VTOP);
		if(row==myGrid.length-1) myFinder.union(encode, VBOTTOM);
		if(inBounds(row-1,col) && isOpen(row-1,col)) {
			myFinder.union(encode, (row-1)*myGrid.length+col);
		}
		if(inBounds(row+1,col) && isOpen(row+1,col)) {
			myFinder.union(encode, (row+1)*myGrid.length+col);
		}
		if(inBounds(row,col-1) && isOpen(row,col-1)) {
			myFinder.union(encode, (row)*myGrid.length+col-1);
		}
		if(inBounds(row,col+1) && isOpen(row,col+1)) {
			myFinder.union(encode, (row)*myGrid.length+col+1);
		}
	}



	@Override
	public boolean isOpen(int row, int col) {
		if (!inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		return myGrid[row][col];
	}

	@Override
	public boolean isFull(int row, int col) {
		if (!inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		int encode = (row*myGrid.length)+col;
		return myFinder.connected(encode, VTOP);
	}

	@Override
	public boolean percolates() {
		return myFinder.connected(VTOP, VBOTTOM);
	}

	@Override
	public int numberOfOpenSites() {
		return myOpenCount;
	}

	private boolean inBounds(int row, int col) {
		if (row<0 || row >= myGrid.length) return false;
		if (col<0 || col >= myGrid[0].length) return false;
		return true;
	}
	
}

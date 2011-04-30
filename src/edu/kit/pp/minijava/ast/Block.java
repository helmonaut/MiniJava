package edu.kit.pp.minijava.ast;

import java.util.LinkedList;
import java.util.List;

public class Block extends Statement {

	private List<BlockStatement> _blockStatements;

	public Block() {
		_blockStatements = new LinkedList<BlockStatement>();
	}

	public void add(BlockStatement bs) {
		_blockStatements.add(bs);
	}
}

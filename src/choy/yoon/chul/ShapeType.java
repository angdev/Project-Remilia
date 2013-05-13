package choy.yoon.chul;

public class ShapeType {

	private ShapeEnumType type_;	
	
	public ShapeType(int id) {
		switch(id) {
		case R.id.action_dot:
			type_ = ShapeEnumType.kShapeDot;
			break;
		case R.id.action_line:
			type_ = ShapeEnumType.kShapeLine;
			break;
		default:
			type_ = ShapeEnumType.kShapeNull;
			break;
		}
	}
	
	public ShapeType(ShapeEnumType type) {
		type_ = type;
	}
	
	public ShapeEnumType GetType() {
		return type_;
	}
}

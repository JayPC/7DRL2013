package shadows;

public class MapPart {
	//int width;
	//int height;
	public Tile[][] partTiles;
	
	public MapPart(int swidth, int sheight){
		partTiles = new Tile[swidth][sheight];
	}
	public MapPart(){
		partTiles = new Tile[1][1];
	}
	
}

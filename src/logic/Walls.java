package logic;

public class Walls {

final static int wall0height = 600;
final static int wall1height = 500;
final static int wall2height = 400;

//Method takes worldX and framey + imageSize (so the lowest y point of the character) and tells you if it's beneath the walls or not
public static boolean collision (int x, int y) {
	
	if (x >= 4300 && x <= 5200 ) {
		if (y > wall1height) {
			return true;
		}
	}else if(x >= 5600 && x <= 7600) {
		if(y > wall0height) {
			return true;
		}
	}else if(x > 7600 && x <= 8000){
		if (y > wall1height) {
			return true;
		}
	}else if(x > 8000 && x <= 8400){
		if (y > wall2height) {
			return true;
		}
	}else if(x > 8700 && x <= 9800){
		if (y > wall2height) {
			return true;
		}
	}else if(x > 10000 && x <= 10400){
		if (y > wall2height) {
			return true;
		}
	}else if(x > 10400 && x <= 11000 ){
		if (y > wall2height) {
			return true;
		}
	}else if(x > 11000 && x <= 12200 ){
		if(y > wall0height) {
			return true;
		}
	}else if(x > 12200 && x <= 12800 ){
		if (y > wall0height) {
			return true;
		}
	}else if(x > 12800 && x <= 13400 ){
		if (y > wall0height) {
			return true;
		}
	}else if(x > 13400 && x <= 13800 ){
		if (y > wall1height) {
			return true;
		}
	}else if(x > 14000 && x <= 14400 ){
		if (y > wall1height) {
			return true;
		}
	}else if(x > 14400 && x <= 14800 ){
		if(y > wall0height) {
			return true;
		}
	}else if(x > 15000 && x <= 19200 ){
		if(y > wall0height) {
			return true;
		}
	}else if(x > 19200 && x <= 21000 ){
		if (y > wall1height) {
			return true;
		}
	}
	return false;
}
}

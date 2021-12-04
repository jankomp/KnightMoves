package logic;

public class GroundLevel {
final static int normalGround = 600;
final static int elevation1 = 500;
final static int elevation2 = 400;
final static int hole = 1400;

public static int getGround (int x) {
	//tells you on which height the ground is on your position
	if (x <= 4200) {
		return normalGround;
	} else if(x > 4200 && x <= 5200) {
		return elevation1;
	} else if(x > 5200 && x <= 5600) {
		return hole;
	}else if(x > 5600 && x <= 7600){
		return normalGround;
	}else if(x > 7600 && x <= 8000){
		return elevation1;
	}else if(x > 8000 && x <= 8400){
		return elevation2;
	}else if(x > 8400 && x <= 8600){
		return hole;
	}else if(x > 8600 && x <= 9800){
		return elevation2;
	}else if(x > 9800 && x <= 10000){
		return hole;
	}else if(x > 10000 && x <= 10400){
		return elevation2;
	}else if(x > 10400 && x <= 10600){
		return hole;
	}else if(x > 10600 && x <= 11000 ){
		return elevation2;
	}else if(x > 11000 && x <= 12200 ){
		return normalGround;
	}else if(x > 12200 && x <= 12400 ){
		return hole;
	}else if(x > 12400 && x <= 12800 ){
		return normalGround;
	}else if(x > 12800 && x <= 13000 ){
		return hole;
	}else if(x > 13000 && x <= 13400 ){
		return normalGround;
	}else if(x > 13400 && x <= 13800 ){
		return elevation1;
	}else if(x > 13800 && x <= 14000 ){
		return hole;
	}else if(x > 14000 && x <= 14400 ){
		return elevation1;
	}else if(x > 14400 && x <= 14800 ){
		return normalGround;
	}else if(x > 14800 && x <= 15000 ){
		return hole;
	}else if(x > 15000 && x <= 19200 ){
		return normalGround;
	}else if(x > 19200 && x <= 21000 ){
		return elevation1;
	}else {
		return hole;
	}
}

}

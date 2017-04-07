package Game.main;

import java.awt.event.KeyEvent;

public class CheatCode {

	static public boolean UpGame = false;

	static private boolean [] ATTARI = new boolean [8]; //UP UP DOWN DOWN LEFT RIGHT ENTER . <- resets code
	public CheatCode(){
		resetATTARI();
	}
	public static void resetATTARI(){
		ATTARI = KeyInput.memset(ATTARI, 8, false);
	}
	static public boolean checkCode(int key){
		if(ATTARI[0]){
			if(ATTARI[1]){
				if(ATTARI[2]){
					if(ATTARI[3]){
						if(ATTARI[4]){
							if(ATTARI[5]){
								if(ATTARI[6]){
									resetATTARI();
									return true;
								} else if(key == KeyEvent.VK_ENTER){
									ATTARI[6] = true;
								}
							} else if(key == KeyEvent.VK_RIGHT){
								ATTARI[5] = true;
							}
						}else if(key == KeyEvent.VK_LEFT){
							ATTARI[4] = true;
						}
					}else if(key == KeyEvent.VK_DOWN){
						ATTARI[3] = true;
					}
				}else if(key == KeyEvent.VK_DOWN){
					ATTARI[2] = true;
				}
			}else if(key == KeyEvent.VK_UP){
				ATTARI[1] = true;
			}
		}else if(key == KeyEvent.VK_UP){
			ATTARI[0] = true;
		}else {
			resetATTARI();
		}
		return false;	
	}
	
	static public int numbers(int key){
		if(key == KeyEvent.VK_1 || key == KeyEvent.VK_NUMPAD1){
			return 1;
		}else if(key == KeyEvent.VK_2 || key == KeyEvent.VK_NUMPAD2){
			return 2;
		}else if(key == KeyEvent.VK_3 || key == KeyEvent.VK_NUMPAD3){
			return 3;
		}else if(key == KeyEvent.VK_4 || key == KeyEvent.VK_NUMPAD4){
			return 4;
		}else if(key == KeyEvent.VK_5 || key == KeyEvent.VK_NUMPAD5){
			return 5;
		}else if(key == KeyEvent.VK_6 || key == KeyEvent.VK_NUMPAD6){
			return 6;
		}else if(key == KeyEvent.VK_7 || key == KeyEvent.VK_NUMPAD7){
			return 7;
		}else if(key == KeyEvent.VK_8 || key == KeyEvent.VK_NUMPAD8){
			return 8;
		}else if(key == KeyEvent.VK_9 || key == KeyEvent.VK_NUMPAD9){
			return 9;
		}else if(key == KeyEvent.VK_0 || key == KeyEvent.VK_NUMPAD0){
			return 0;
		}else{
			return -1;
		}
	}
	static public boolean exit(int key){
		if(key == KeyEvent.VK_PERIOD){
			return true;
		}
		return false;
	}
}

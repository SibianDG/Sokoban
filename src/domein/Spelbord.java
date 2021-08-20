package domein;

import exceptions.FouteZetException;

import java.util.HashMap;
import java.util.Map;

public class Spelbord {

	private int spelbordID;
	private boolean isVoltooid;
	private int aantalStappen = 0;
	private Spel spel;

	private Veld[][] bord;
	private int positieManX, positieManY;
	
	private Map<Integer, Veld> nieuweVelden = new HashMap<>();
	private Map<Integer, Veld> oudeVelden = new HashMap<>();

	Map<Integer, int[]> mogelijkhedenRichting = Map.of(
			1, new int[] {-1, 0},
			2, new int[] {0, 1},
			3, new int[] {1, 0},
			4, new int[] {0, -1}
	);

	Map<Type, Character> mogelijkhedenNaVerplaatsingKistOfMan = Map.of(
			Type.MANDOEL, '.',
			Type.MAN, ' ',
			Type.LEEG, 'x',
			Type.DOEL, 'S'
	);

	Map<Integer, Character> mogelijkhedenTypesNieuwSpelbord = Map.of(
			1, '#',
			2, '.',
			3, 'M',
			4, 'x',
			5, ' '
	);

	public Spelbord(int spelbordID) {
		setSpelbordID(spelbordID);
	}

	public Spelbord(Spel spel) {
		this.bord = new Veld[10][10];
		for(int i = 0; i < bord.length; i++) {
			for(int j = 0; j < bord[i].length; j++) {
				this.bord[i][j] = new Veld(' ');
			}
		}
		setSpel(spel);
	}

	@Override
	public String toString() {
		StringBuilder bordString = new StringBuilder();
		for(Veld[] rij: bord) {
			for(Veld col: rij) {
				bordString.append(col.getType().getTeken());
			}
			bordString.append(String.format("%n"));
		}
		return bordString.toString();
	}

	public String[][] toStringArray() {
		String[] spelbord1D = toString().split("\\n");
		String[][] spelbord2D = new String[10][10];
		int index = 0;
		for(String rij: spelbord1D) {
			spelbord2D[index++] = rij.split("");
		}
		return spelbord2D;
	}

	public boolean isVoltooid() {
		return isVoltooid;
	}

	public Veld[][] getBord() {
		return bord;
	}

	public int getSpelbordID() {
		return spelbordID;
	}

	public void verplaatsSpeler(int keuzeRichting) {
		int[] positieMetRichting = geefNieuwePositie(positieManX, positieManY, keuzeRichting);
		if (controleerRichting(keuzeRichting, positieMetRichting[0], positieMetRichting[1])) {
			verplaatsKistAlsManErTegenKomt(keuzeRichting, positieMetRichting[0], positieMetRichting[1]);
			verplaatsManMetControleDoel(positieMetRichting[0], positieMetRichting[1]);
			bord[positieManX][positieManY] = new Veld(mogelijkhedenNaVerplaatsingKistOfMan.get(bord[positieManX][positieManY].getType()));
			aantalStappen++;
			setPositieManX(positieMetRichting[0]);
			setPositieManY(positieMetRichting[1]);
			controleerGewonnen();
		} else {
			throw new FouteZetException();
		}
	}

	private void verplaatsKistAlsManErTegenKomt(int richting, int x, int y){
		if(bord[x][y].getType() == Type.KIST || bord[x][y].getType() == Type.KISTDOEL) {
			int[] temp = geefNieuwePositie(x, y, richting);
			bord[temp[0]][temp[1]] = new Veld(mogelijkhedenNaVerplaatsingKistOfMan.get(bord[temp[0]][temp[1]].getType()));
		}
	}

	private void verplaatsManMetControleDoel(int x, int y){
		if(bord[x][y].getType() == Type.DOEL || bord[x][y].getType() == Type.KISTDOEL) {
			bord[x][y] = new Veld('D');
		} else {
			bord[x][y] = new Veld('M');
		}
	}

	private boolean controleerRichting(int keuzeRichting, int x, int y) {
		if (x < 0 || y < 0 || x >= 10 || y >= 10){
			throw new FouteZetException();
		}
		Type typeOpPositie = bord[x][y].getType();
		if(typeOpPositie == Type.MUUR) {
			return false;
		} else if(typeOpPositie == Type.KIST || typeOpPositie == Type.KISTDOEL) {
			int[] temp = geefNieuwePositie(x, y, keuzeRichting);
			Type typeOpPositiePlus2 = bord[temp[0]][temp[1]].getType();
			return typeOpPositiePlus2 == Type.LEEG || typeOpPositiePlus2 == Type.DOEL;
		}
		return true;
	}

	private int[] geefNieuwePositie(int x, int y, int richting) {
		int[] richtingArr  = mogelijkhedenRichting.get(richting);
		x += richtingArr[0];
		y += richtingArr[1];
		return new int[] {x, y};
	}

	private void controleerGewonnen() {
		boolean gewonnen = true;
		for(Veld[] velds : bord) {
			for(Veld veld : velds) {
				if(veld.getType() == Type.DOEL || veld.getType() == Type.MANDOEL) {
					gewonnen = false;
					break;
				}
			}
		}
		this.isVoltooid = gewonnen;
	}

	public void voerActieUitOpPositie(int plaats, int veldTeken) {
		bord[plaats/10][plaats%10] = new Veld(mogelijkhedenTypesNieuwSpelbord.get(veldTeken));
	}

	public void setSpelbordID(int spelbordID) {
		this.spelbordID = spelbordID;
	}

	public Spel getSpel() {
		return spel;
	}

	public void setSpel(Spel spel) {
		this.spel = spel;
	}

	public int getAantalStappen() {
		return aantalStappen;
	}

	private void setPositieManX(int positieManX) {
		this.positieManX = positieManX;
	}

	private void setPositieManY(int positieManY) {
		this.positieManY = positieManY;
	}

	public final void setBord(Veld[][] bord) {
		this.bord = bord;
		for(int i = 0; i < bord.length; i++) {
			for(int j = 0; j < bord[i].length; j++) {
				if(bord[i][j] == null) {
					bord[i][j] = new Veld(' ');
				} else if (bord[i][j].getType() == Type.MAN) {
					setPositieManX(i);
					setPositieManY(j);
				}
			}
		}
	}

	public void controleerNieuwToegevoegdSpelbord() {
		int mannetje = 0, kisten = 0, doelen = 0;
		for(Veld[] velds : bord) {
			for(Veld veld : velds) {
				if(veld.getType() == Type.MAN) {
					mannetje++;
				} else if(veld.getType() == Type.KIST) {
					kisten++;
				} else if(veld.getType() == Type.DOEL) {
					doelen++;
				}
			}
		}
		if(mannetje != 1) {
			throw new IllegalArgumentException("errorSpelbordZonderMan");
		}
		if((doelen == 0 || kisten == 0) || kisten != doelen) {
			throw new IllegalArgumentException("errorKistDoel");
		}
	}

	public void wijzigSpelbordOpPositie(int positie, int actie) {
		this.oudeVelden.put(positie, bord[positie/10][positie%10]);
		
		Veld nieuwVeld;
		
		if (actie == 5) {
			nieuwVeld = new Veld(' ');
		} else {
			nieuwVeld = new Veld(mogelijkhedenTypesNieuwSpelbord.get(actie));
		}
		bord[positie/10][positie%10] = nieuwVeld;
		this.nieuweVelden.put(positie, nieuwVeld);

	}
	
	public Veld geefVeldOpPositie(int positie) {
		return bord[positie/10][positie%10];
	}
	
	public int geefXwaardeVeldOpPositie(int positie) {
		return positie / 10;
	}
	
	public int geefYwaardeVeldOpPositie(int positie) {
		return positie % 10;
	}
	
	public Map<Integer, Veld> getOudeVelden() {
		return this.oudeVelden;
	}
	
	public Map<Integer, Veld> getNieuweVelden() {
		return this.nieuweVelden;
	}

	public void setNietVoltooid() {
		this.isVoltooid = false;
	}

	public void reset() {
	}
}
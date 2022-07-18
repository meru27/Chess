package juego.control;

import static juego.modelo.Color.BLANCO;
import static juego.modelo.Color.NEGRO;
import static juego.modelo.Tipo.PEON;
import static juego.modelo.Tipo.REY;
import static juego.modelo.Tipo.CABALLO;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import juego.modelo.Celda;
import juego.modelo.Color;
import juego.modelo.Tablero;
import juego.modelo.Tipo;

/**
 * Pruebas avanzadas para la promoción de peón a dama con color negro
 * con captura.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20190813
 */
@DisplayName("Test avanzados de promoción de peón a dama con captura")
public class ArbitroPromocionPeonNegroADamaNegraConCapturaTest {
	
	/** Tablero. */
	private Tablero tablero;
	
	/** Arbitro. */
	private Arbitro arbitro;

	/**
	 * Inicializa el tablero.
	 */
	//  @formatter:off
	/* Partimos de un tablero que debe tener este estado.
	*   a  b  c  d  e  f  g  h  
	* 8 -- -- RN -- -- -- -- --
	* 7 -- -- -- -- -- -- -- --
	* 6 -- -- -- -- -- -- -- --
	* 5 -- -- -- -- -- -- -- --
	* 4 -- -- -- -- -- -- -- -- 
	* 3 -- -- -- -- -- -- -- --
	* 2 PN -- -- -- -- -- PB PB
	* 1 -- CB -- -- -- -- -- RB
	*/
	// @formatter:on
	@BeforeEach
	void iniciar() {
		// Preparamos tablero
		tablero = new Tablero();
		arbitro = new Arbitro(tablero);
		Tipo[] tipos = { REY, PEON, PEON, PEON, CABALLO, REY };
		Color[] colores = { NEGRO, NEGRO, BLANCO, BLANCO, BLANCO, BLANCO };
		int[][] posiciones = { { 0, 2 }, { 6, 0 }, { 6, 6 }, { 6, 7 }, {7, 1}, { 7, 7 } };
		arbitro.colocarPiezas(tipos, colores, posiciones);
		// cambiar turno para que mueva con negras
		arbitro.cambiarTurno();
	}
	
	/**
	 * Comprobar promoción de peón negro a dama negra.
	 */
	@DisplayName("Comprobar promoción de peón negro a dama negra con captura")
	@Test
	void comprobarPromociónDePeonNegroADamaNegra() {

		// realizamos movimiento
		Celda origen = tablero.obtenerCelda(6, 0);
		Celda destino = tablero.obtenerCelda(7, 1);

		assertThat("El movimiento se detecta como no legal", arbitro.esMovimientoLegal(origen, destino), is(true));
		assertThat("Numero de piezas negras incorrecto", tablero.obtenerNumeroPiezas(Color.NEGRO), is(2));
		assertThat("Numero de piezas blancas incorrecto", tablero.obtenerNumeroPiezas(Color.BLANCO), is(4));
		
		arbitro.mover(origen, destino);
		
		assertAll("Pieza no promocionada correctamente a dama blanca",
				() -> assertThat("Debería contener una dama", destino.obtenerPieza().obtenerTipo(), is(Tipo.DAMA)),
				() -> assertThat("Debería contener pieza negra", destino.obtenerColorDePieza(), is(Color.NEGRO)),
				() -> assertThat("El número de piezas negras no debería cambiar", tablero.obtenerNumeroPiezas(Color.NEGRO), is(2)),
				() -> assertThat("El número de piezas blancas debería disminuir en 1", tablero.obtenerNumeroPiezas(Color.BLANCO), is(3)));
	
	}
		

	/**
	 * Comprobar jaque mate al promocionar el peón negro a dama negra. No hay movimientos
	 * posibles.
	 */

	@DisplayName("Comprobar jaque mate al promocionar peón negro a dama negra con captura")
	@Test
	void comprobarJaqueMateAlPromocionarPeonNegroADamaNegra() {

		// realizamos movimiento
		Celda origen = tablero.obtenerCelda(6, 0);
		Celda destino = tablero.obtenerCelda(7, 1);

		
		arbitro.mover(origen, destino);
		arbitro.cambiarTurno();
		
		// Comprobar que el rey blanco está amenazado y en jaque mate
		assertThat("Debería detectarse el jaque", arbitro.estaEnJaque(BLANCO), is(true));
		assertThat("Debería detectarse el jaque mate", arbitro.estaEnJaqueMate(), is(true));

		// para cualquier movimiento legal de peón o rey blanco sigue amenazado
		// por lo tanto está en jaque mate y solo queda rendirse

		int[][] ori =  { { 7, 7}, {6 , 6}, {6, 6}, {6, 7}, {6, 7}};
		int[][] dest = { { 7, 6}, {5,  6}, {4, 6}, {5, 7}, {4, 7}};

		for (int i = 0; i < ori.length; i++) {
			origen = tablero.obtenerCelda(ori[i][0], ori[i][1]);
			destino = tablero.obtenerCelda(dest[i][0], dest[i][1]);
			arbitro.mover(origen, destino);
			assertThat("Debería detectarse el jaque", arbitro.estaEnJaque(BLANCO), is(true));
			arbitro.mover(destino, origen); // deshacer
		}
	}
}

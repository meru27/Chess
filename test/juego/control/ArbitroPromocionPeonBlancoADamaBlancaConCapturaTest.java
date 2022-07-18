package juego.control;

import static juego.modelo.Color.BLANCO;
import static juego.modelo.Color.NEGRO;
import static juego.modelo.Tipo.PEON;
import static juego.modelo.Tipo.CABALLO;
import static juego.modelo.Tipo.REY;
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
 * Pruebas avanzadas para la promoción de peón a dama con color blanco
 * realizando captura.
 * 
 * @author <a href="mailto:rmartico@ubu.es">Raúl Marticorena Sánchez</a>
 * @version 1.0 20190813
 */
@DisplayName("Test avanzados de promoción de peón a dama con captura")
public class ArbitroPromocionPeonBlancoADamaBlancaConCapturaTest {
	
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
	* 8 -- CN -- -- -- -- -- RN
	* 7 PB -- -- -- -- -- PN PN
	* 6 -- -- -- -- -- -- -- --
	* 5 -- -- -- -- -- -- -- --
	* 4 -- -- -- -- -- -- -- -- 
	* 3 -- -- -- -- -- -- -- --
	* 2 -- -- -- -- -- -- -- --
	* 1 -- -- RB -- -- -- -- --
	*/
	// @formatter:on
	@BeforeEach
	void iniciar() {
		// Preparamos tablero
		tablero = new Tablero();
		arbitro = new Arbitro(tablero);
		Tipo[] tipos = { CABALLO, REY, PEON, PEON, PEON, REY };
		Color[] colores = { NEGRO, NEGRO, BLANCO, NEGRO, NEGRO, BLANCO };
		int[][] posiciones = { {0, 1}, { 0, 7 }, { 1, 0 }, { 1, 6 }, { 1, 7 }, { 7, 2 } };
		arbitro.colocarPiezas(tipos, colores, posiciones);
	}
	
	/**
	 * Comprobar promoción de peón blanco a dama blanca con captura.
	 */
	@DisplayName("Comprobar promoción de peón blanco a dama blanca con captura")
	@Test
	void comprobarPromociónDePeonBlancoADamaBlanca() {
		// realizamos movimiento
		Celda origen = tablero.obtenerCelda(1, 0);
		Celda destino = tablero.obtenerCelda(0, 1);

		assertThat("El movimiento se detecta como no legal", arbitro.esMovimientoLegal(origen, destino), is(true));
		assertThat("Numero de piezas negras incorrecto", tablero.obtenerNumeroPiezas(Color.NEGRO), is(4));
		assertThat("Numero de piezas blancas incorrecto", tablero.obtenerNumeroPiezas(Color.BLANCO), is(2));
		
		arbitro.mover(origen, destino);
		
		assertAll("Pieza no promocionada correctamente a dama blanca",
				() -> assertThat("Debería contener una dama", destino.obtenerPieza().obtenerTipo(), is(Tipo.DAMA)),
				() -> assertThat("Debería contener pieza blanca", destino.obtenerColorDePieza(), is(Color.BLANCO)),
				() -> assertThat("El número de piezas blancas no debería cambiar", tablero.obtenerNumeroPiezas(Color.BLANCO), is(2)),
				() -> assertThat("El número de piezas negras debería disminuir en 1", tablero.obtenerNumeroPiezas(Color.NEGRO), is(3)));
	}
		

	/**
	 * Comprobar jaque mate al promocionar el peón blanco a dama blanca. No ha movimientos posibles.
	 */

	@DisplayName("Comprobar jaque mate al promocionar peón blanco a dama blanca con captura")
	@Test
	void comprobarJaqueMateAlPromocionarPeonBlancoADamaBlanca() {

		// realizamos movimiento
		Celda origen = tablero.obtenerCelda(1, 0);
		Celda destino = tablero.obtenerCelda(0, 1);

		
		arbitro.mover(origen, destino);
		arbitro.cambiarTurno();
		
		// Comprobar que el rey negro está amenazado y en jaque mate
		assertThat("Debería detectarse el jaque", arbitro.estaEnJaque(NEGRO), is(true));
		assertThat("Debería detectarse el jaque mate", arbitro.estaEnJaqueMate(), is(true));

		// para cualquier movimiento legal del peón o rey negro sigue amenazado
		// por lo tanto está en jaque mate y solo queda rendirse

		int[][] ori =  { { 0, 7}, { 1, 6}, { 1, 6}, { 1, 7}, { 1, 7} };
		int[][] dest = { { 0, 6}, { 2, 6}, { 3, 6}, { 2, 7}, { 3, 7} };

		for (int i = 0; i < ori.length; i++) {
			origen = tablero.obtenerCelda(ori[i][0], ori[i][1]);
			destino = tablero.obtenerCelda(dest[i][0], dest[i][1]);
			arbitro.mover(origen, destino);
			assertThat("Debería detectarse el jaque", arbitro.estaEnJaque(NEGRO), is(true));
			arbitro.mover(destino, origen); // deshacer
		}
	}
}

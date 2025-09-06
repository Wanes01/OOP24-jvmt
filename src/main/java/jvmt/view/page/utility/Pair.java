package jvmt.view.page.utility;

/**
 * A simple data holder to store two items.
 * <p>
 * Class provided by Prof. Viroli:
 * </p>
 * 
 * @see <a href=
 *      "https://bitbucket.org/mviroli/oop2024-esami/src/master/a01a/e2/Pair.java">Original
 *      class</a>
 * 
 * @param <A>    the type of the first element to be hold.
 * @param <B>    the type of the second element to be hold.
 * @param first  the first element of type {@code A} to hold.
 * @param second the second element of type {@code B} to hold.
 */
public record Pair<A, B>(A first, B second) {
}

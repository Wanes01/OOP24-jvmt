package jvmt.navigator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jvmt.view.window.api.Window;
import jvmt.controller.navigator.api.PageId;
import jvmt.controller.navigator.api.PageNavigator;
import jvmt.controller.navigator.impl.PageNavigatorImpl;
import jvmt.view.page.api.Page;
import jvmt.view.page.api.SwingPage;
import jvmt.view.window.impl.SwingWindow;

/**
 * Tests a {@link PageNavigatorImpl} functionalities.
 * 
 * @see PageNavigator
 * @see PageNavigatorImpl
 * 
 * @author Emir Wanes Aouioua
 */
class PageNavigatorImplTest {

    private final Window window = new SwingWindow();
    private Map<PageId, Page> navLinks;
    private PageNavigator navigator;

    @BeforeEach
    void setUp() {
        this.navLinks = new EnumMap<>(PageId.class);
        this.navigator = new PageNavigatorImpl(this.window);
        this.fillNavigatorWithRandomPages();
    }

    @Test
    void testGetCurrentPageId() {
        this.navigateAllPagesAndAssertEquals(
                pid -> pid,
                pid -> this.navigator.getCurrentPageId(),
                pid -> "The PageId " + pid + "is not recognized on the navigator");

        // mock page
        final Page mock = new SwingPage() {
            @Override
            protected void setHandlers() {
            }
        };

        /**
         * Asserts that a page not registered within this navigator doesn't have a
         * PageId.
         */
        this.window.setCurrentPage(mock);
        assertEquals(Optional.empty(), this.navigator.getCurrentPageId());
    }

    @Test
    void testNavigatesToPage() {
        this.navigateAllPagesAndAssertEquals(
                this.navLinks::get,
                pid -> this.window.getCurrentPage(),
                pid -> "The visualization did not move to " + pid);

        /*
         * Asserts that non-registered pages can't be browsed with this navigator.
         */
        this.navigator = new PageNavigatorImpl(this.window);
        this.getPageIdsAsList().forEach(
                pid -> assertThrows(
                        IllegalArgumentException.class,
                        () -> this.navigator.navigateTo(pid)));
    }

    /**
     * Tries to navigate to each PageId, retrives a value
     * and asserts that it's equals to an expected value.
     * 
     * @param <T>                  the type of value to assert to retrive after
     *                             navigating to a page.
     * @param expectedFunction     a function that maps a PageId to the expected
     *                             value of type {@code T}
     *                             after navigating to the page.
     * @param actualFunction       a function that maps a PageId to the actual
     *                             value of type {@code T}
     * @param errorMessageFunction a function that maps a PageId to an error message
     *                             to show if this test fails.
     */
    private <T> void navigateAllPagesAndAssertEquals(
            final Function<PageId, T> expectedFunction,
            final Function<PageId, Optional<T>> actualFunction,
            final Function<PageId, String> errorMessageFunction) {
        this.getPageIdsAsList().forEach(pid -> {
            this.navigator.navigateTo(pid);

            final Optional<T> actual = actualFunction.apply(pid);
            if (actual.isEmpty()) {
                fail(errorMessageFunction.apply(pid));
            }
            assertEquals(expectedFunction.apply(pid), actual.get());
        });
    }

    /**
     * Fills the navigator with mock instances of {@link Page}.
     */
    private void fillNavigatorWithRandomPages() {
        this.getPageIdsAsList().forEach(pid -> {
            final Page mockPage = new SwingPage() {
                @Override
                protected void setHandlers() {
                }
            };
            this.navLinks.put(pid, mockPage);
            this.navigator.registerPage(pid, mockPage);
        });
    }

    /**
     * Returns all available {@code PageIds} as a list.
     * 
     * @return all {@code PageIds} as a list.
     */
    private List<PageId> getPageIdsAsList() {
        return Arrays.asList(PageId.values());
    }
}

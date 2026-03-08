package ink.ui.structures.elements

/**
 * An individual view element in the UI.
 */
sealed interface UiElement {
    /**
     * An element that contains mechanisms for user interaction.
     *
     * These elements may not be able to be rendered on some UI flows.
     */
    interface Interactive: UiElement

    /**
     * A non-interactive UI element.
     */
    interface Static: UiElement
}

package ca.gbc.comp3074.g79.mapping

object UIPrototypeMapper {

    data class Screen(
        val id: String,
        val name: String,
        val purpose: String,
        val components: List<UIComponent>,
        val navigation: List<NavigationTarget>
    )

    data class UIComponent(
        val type: ComponentType,
        val id: String,
        val purpose: String,
        val interactions: List<Interaction>,
        val dataSource: String? = null
    )

    data class Interaction(
        val type: InteractionType,
        val target: String,
        val action: String
    )

    data class NavigationTarget(
        val from: String,
        val to: String,
        val trigger: String
    )

    enum class ComponentType {
        BUTTON, TEXT_FIELD, LIST, CARD, IMAGE, TEXT_VIEW,
        SWITCH, CHECKBOX, RADIO_BUTTON, FAB, RATING_BAR,
        RECYCLER_VIEW, EDIT_TEXT, SPINNER, MENU_ITEM
    }

    enum class InteractionType {
        CLICK, LONG_CLICK, SWIPE, SUBMIT, ITEM_CLICK,
        TEXT_CHANGE, BACK_PRESS, MENU_SELECT
    }

    // Screen mappings for G79 Restaurant App
    val screens = listOf(
        Screen(
            id = "main",
            name = "Main Dashboard",
            purpose = "Display list of restaurants and primary navigation",
            components = listOf(
                UIComponent(
                    type = ComponentType.RECYCLER_VIEW,
                    id = "restaurant_list",
                    purpose = "Display all restaurants in a scrollable list",
                    dataSource = "restaurant_database",
                    interactions = listOf(
                        Interaction(
                            type = InteractionType.ITEM_CLICK,
                            target = "restaurant_item",
                            action = "open_edit_screen"
                        )
                    )
                ),
                UIComponent(
                    type = ComponentType.BUTTON,
                    id = "add_restaurant_button",
                    purpose = "Navigate to add restaurant form",
                    interactions = listOf(
                        Interaction(
                            type = InteractionType.CLICK,
                            target = "add_button",
                            action = "open_add_restaurant_form"
                        )
                    )
                ),
                UIComponent(
                    type = ComponentType.MENU_ITEM,
                    id = "menu_add",
                    purpose = "Alternative navigation to add restaurant",
                    interactions = listOf(
                        Interaction(
                            type = InteractionType.CLICK,
                            target = "menu_item",
                            action = "open_add_restaurant_alt"
                        )
                    )
                )
            ),
            navigation = listOf(
                NavigationTarget("main", "add_restaurant", "add_button_click"),
                NavigationTarget("main", "add_restaurant_alt", "menu_item_click"),
                NavigationTarget("main", "edit_restaurant", "list_item_click")
            )
        ),
        Screen(
            id = "add_restaurant",
            name = "Add Restaurant",
            purpose = "Form to add new restaurant to database",
            components = listOf(
                UIComponent(
                    type = ComponentType.EDIT_TEXT,
                    id = "name_input",
                    purpose = "Input restaurant name",
                    interactions = listOf(
                        Interaction(
                            type = InteractionType.TEXT_CHANGE,
                            target = "name_field",
                            action = "validate_name_input"
                        )
                    )
                ),
                UIComponent(
                    type = ComponentType.EDIT_TEXT,
                    id = "address_input",
                    purpose = "Input restaurant address",
                    interactions = listOf(
                        Interaction(
                            type = InteractionType.TEXT_CHANGE,
                            target = "address_field",
                            action = "validate_address_input"
                        )
                    )
                ),
                UIComponent(
                    type = ComponentType.EDIT_TEXT,
                    id = "phone_input",
                    purpose = "Input restaurant phone number",
                    interactions = listOf(
                        Interaction(
                            type = InteractionType.TEXT_CHANGE,
                            target = "phone_field",
                            action = "validate_phone_format"
                        )
                    )
                ),
                UIComponent(
                    type = ComponentType.SPINNER,
                    id = "type_selector",
                    purpose = "Select restaurant category/type",
                    interactions = listOf(
                        Interaction(
                            type = InteractionType.ITEM_CLICK,
                            target = "type_spinner",
                            action = "set_restaurant_type"
                        )
                    )
                ),
                UIComponent(
                    type = ComponentType.RATING_BAR,
                    id = "rating_control",
                    purpose = "Set initial restaurant rating",
                    interactions = listOf(
                        Interaction(
                            type = InteractionType.CLICK,
                            target = "rating_bar",
                            action = "update_rating_value"
                        )
                    )
                ),
                UIComponent(
                    type = ComponentType.BUTTON,
                    id = "save_button",
                    purpose = "Save restaurant to database",
                    interactions = listOf(
                        Interaction(
                            type = InteractionType.CLICK,
                            target = "save_btn",
                            action = "submit_restaurant_form"
                        )
                    )
                )
            ),
            navigation = listOf(
                NavigationTarget("add_restaurant", "main", "save_success"),
                NavigationTarget("add_restaurant", "main", "cancel_action")
            )
        ),
        Screen(
            id = "edit_restaurant",
            name = "Edit Restaurant",
            purpose = "Modify or delete existing restaurant details",
            components = listOf(
                UIComponent(
                    type = ComponentType.EDIT_TEXT,
                    id = "edit_name",
                    purpose = "Edit restaurant name",
                    interactions = listOf(
                        Interaction(
                            type = InteractionType.TEXT_CHANGE,
                            target = "name_edit",
                            action = "update_name_field"
                        )
                    )
                ),
                UIComponent(
                    type = ComponentType.EDIT_TEXT,
                    id = "edit_address",
                    purpose = "Edit restaurant address",
                    interactions = listOf(
                        Interaction(
                            type = InteractionType.TEXT_CHANGE,
                            target = "address_edit",
                            action = "update_address_field"
                        )
                    )
                ),
                UIComponent(
                    type = ComponentType.BUTTON,
                    id = "update_button",
                    purpose = "Save changes to restaurant",
                    interactions = listOf(
                        Interaction(
                            type = InteractionType.CLICK,
                            target = "update_btn",
                            action = "update_restaurant_record"
                        )
                    )
                ),
                UIComponent(
                    type = ComponentType.BUTTON,
                    id = "delete_button",
                    purpose = "Remove restaurant from database",
                    interactions = listOf(
                        Interaction(
                            type = InteractionType.CLICK,
                            target = "delete_btn",
                            action = "delete_restaurant_record"
                        )
                    )
                )
            ),
            navigation = listOf(
                NavigationTarget("edit_restaurant", "main", "update_success"),
                NavigationTarget("edit_restaurant", "main", "delete_success"),
                NavigationTarget("edit_restaurant", "main", "back_navigation")
            )
        )
    )

    // Utility functions for mapping analysis
    fun getScreenById(screenId: String): Screen? {
        return screens.find { it.id == screenId }
    }

    fun getAllComponents(): List<UIComponent> {
        return screens.flatMap { it.components }
    }

    fun getComponentsByType(type: ComponentType): List<UIComponent> {
        return getAllComponents().filter { it.type == type }
    }

    fun generateNavigationReport(): Map<String, Any> {
        return mapOf(
            "total_screens" to screens.size,
            "total_components" to getAllComponents().size,
            "navigation_paths" to screens.flatMap { it.navigation }.size,
            "screen_details" to screens.associate { screen ->
                screen.id to mapOf(
                    "name" to screen.name,
                    "components" to screen.components.size,
                    "navigation_targets" to screen.navigation.size
                )
            }
        )
    }

    fun validateMapping(): List<String> {
        val issues = mutableListOf<String>()

        // Check for duplicate component IDs within screens
        screens.forEach { screen ->
            val componentIds = screen.components.map { it.id }
            if (componentIds.distinct().size != componentIds.size) {
                issues.add("Duplicate component IDs in screen: ${screen.name}")
            }
        }

        // Check navigation consistency
        val validScreenIds = screens.map { it.id }
        screens.flatMap { it.navigation }.forEach { nav ->
            if (!validScreenIds.contains(nav.to)) {
                issues.add("Invalid navigation target: ${nav.to}")
            }
        }

        return issues
    }
}
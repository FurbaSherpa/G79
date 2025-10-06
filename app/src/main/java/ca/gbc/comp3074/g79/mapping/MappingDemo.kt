package ca.gbc.comp3074.g79.mapping

fun main() {
    // This would be used for testing the mapping independently
    val analysis = MappingAnalysis()

    println("🔍 UI Prototype Mapping Demo")
    println("=" .repeat(50))

    // Generate complete report
    val report = analysis.generateCompleteMappingReport()
    println(report)

    // Show JSON export
    println("JSON Export:")
    println(analysis.exportMappingToJSON())

    // Demo specific queries
    println("\n🔧 Mapping Utility Demo:")
    val mapper = UIPrototypeMapper

    // Get specific screen
    val mainScreen = mapper.getScreenById("main")
    println("Main Screen Components: ${mainScreen?.components?.size ?: 0}")

    // Count button components
    val buttons = mapper.getComponentsByType(UIPrototypeMapper.ComponentType.BUTTON)
    println("Total Button Components: ${buttons.size}")

    // Navigation analysis
    val totalInteractions = mapper.getAllComponents().sumOf { it.interactions.size }
    println("Total User Interactions Mapped: $totalInteractions")
}
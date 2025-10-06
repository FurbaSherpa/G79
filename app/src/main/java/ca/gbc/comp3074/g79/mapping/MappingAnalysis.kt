package ca.gbc.comp3074.g79.mapping

import android.os.Build
import androidx.annotation.RequiresApi

class MappingAnalysis {

    fun generateCompleteMappingReport(): String {
        val mapper = UIPrototypeMapper
        val report = StringBuilder()

        report.appendLine("=== G79 RESTAURANT APP UI MAPPING ANALYSIS ===")
        report.appendLine()

        // Basic Statistics
        val navReport = mapper.generateNavigationReport()
        report.appendLine("SCREEN STATISTICS:")
        report.appendLine("• Total Screens: ${navReport["total_screens"]}")
        report.appendLine("• Total Components: ${navReport["total_components"]}")
        report.appendLine("• Navigation Paths: ${navReport["navigation_paths"]}")
        report.appendLine()

        // Screen Details
        report.appendLine("SCREEN DETAILS:")
        mapper.screens.forEach { screen ->
            report.appendLine("📱 ${screen.name} (${screen.id})")
            report.appendLine("   Purpose: ${screen.purpose}")
            report.appendLine("   Components: ${screen.components.size}")
            report.appendLine("   Navigation Targets: ${screen.navigation.size}")

            screen.components.forEach { component ->
                report.appendLine("   └─ ${component.type}: ${component.id}")
                report.appendLine("      Purpose: ${component.purpose}")
                if (component.dataSource != null) {
                    report.appendLine("      Data: ${component.dataSource}")
                }
            }
            report.appendLine()
        }

        // Component Type Analysis
        report.appendLine("COMPONENT TYPE ANALYSIS:")
        UIPrototypeMapper.ComponentType.values().forEach { type ->
            val components = mapper.getComponentsByType(type)
            if (components.isNotEmpty()) {
                report.appendLine("• $type: ${components.size} instances")
            }
        }
        report.appendLine()

        // Navigation Flow
        report.appendLine("NAVIGATION FLOW:")
        mapper.screens.forEach { screen ->
            screen.navigation.forEach { nav ->
                report.appendLine("• ${nav.from} → ${nav.to} (trigger: ${nav.trigger})")
            }
        }
        report.appendLine()

        // Validation Results
        val validationIssues = mapper.validateMapping()
        if (validationIssues.isEmpty()) {
            report.appendLine("✅ MAPPING VALIDATION: PASSED")
        } else {
            report.appendLine("❌ MAPPING VALIDATION ISSUES:")
            validationIssues.forEach { issue ->
                report.appendLine("   • $issue")
            }
        }

        return report.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun exportMappingToJSON(): String {
        // Simple JSON representation of the mapping
        return """
        {
            "app_name": "G79 Restaurant Guide",
            "mapping_version": "1.0",
            "screens_count": ${UIPrototypeMapper.screens.size},
            "total_components": ${UIPrototypeMapper.getAllComponents().size},
            "last_updated": "${java.time.LocalDate.now()}"
        }
        """.trimIndent()
    }
}
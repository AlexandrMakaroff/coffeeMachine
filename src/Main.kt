package machine

import kotlin.system.exitProcess

enum class States(val msg: String) {
    START("Write action (buy, fill, take, remaining, exit):"),
    BYU("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
}

object CoffeeMachine {
    private var water = 400
    private var milk = 540
    private var beans = 120
    private var cups = 9
    private var money = 550

    var state = States.START

    private fun makeCoffee(water: Int, milk: Int, beans: Int, money: Int) {
        if (CoffeeMachine.water - water < 0) {
            println("Sorry, not enough water!\n")
        } else if (CoffeeMachine.beans - beans < 0) {
            println("Sorry, not enough beans!\n")
        } else if (CoffeeMachine.cups - 1 < 0) {
            println("Sorry, not enough cups!\n")
        } else if (CoffeeMachine.milk - milk < 0) {
            println("Sorry, not enough milk!\n")
        } else {
            CoffeeMachine.water -= water
            CoffeeMachine.milk -= milk
            CoffeeMachine.beans -= beans
            CoffeeMachine.money += money
            CoffeeMachine.cups -= 1
            println("I have enough resources, making you a coffee!\n")
        }
    }

    private fun remaining() {
        println(
            """
            The coffee machine has:
            $water of water
            $milk of milk
            $beans of coffee beans
            $cups of disposable cups
            $$money of money
            """.trimIndent()
        )
        println()
    }

    private fun fill() {
        println("Write how many ml of water do you want to add:")
        this.water += readLine()!!.toInt()
        println("Write how many ml of milk do you want to add:")
        this.milk += readLine()!!.toInt()
        println("Write how many grams of coffee beans do you want to add:")
        this.beans += readLine()!!.toInt()
        println("Write how many disposable cups of coffee do you want to add:")
        this.cups += readLine()!!.toInt()
        println()
    }

    private fun start(option: String) {
        when (option) {
            "buy" -> state = States.BYU
            "fill" -> fill()
            "take" -> println("I gave you $${this.money}\n").also { this.money = 0 }
            "remaining" -> remaining()
            "exit" -> exitProcess(0)
        }
    }

    private fun buy(option: String) {
        when (option) {
            "1" -> makeCoffee(250, 0, 16, 4)
            "2" -> makeCoffee(350, 75, 20, 7)
            "3" -> makeCoffee(200, 100, 12, 6)
            "back" -> state = States.START
        }
        state = States.START
    }

    fun toDo(option: String) {
        when (state) {
            States.START -> start(option)
            States.BYU -> buy(option)
        }
    }
}

fun main() {
    while (true) {
        println(CoffeeMachine.state.msg)
        CoffeeMachine.toDo(readLine()!!)
    }
}

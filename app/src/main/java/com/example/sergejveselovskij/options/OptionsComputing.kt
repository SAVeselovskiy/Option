package org.kpfu.options

import java.util.*

/**
 * Created by aleksandrterentev on 26.11.16.
 */


class OptionsComputing{

    enum class SharePriceBehavior{
        RISE, DROP, NOT_CHANGED
    }

    class IterationValue{
        val gammaNext: Double
        val bettaNext: Double
        val ro: Double
        val S: Double
        val B: Double
        val X: Double
        val sharePriceBehavior: SharePriceBehavior


        constructor(gammaNext: Double, bettaNext: Double, ro: Double, S: Double, B: Double, X:Double,
                    sharePriceBehavior: SharePriceBehavior) {
            this.gammaNext = gammaNext
            this.bettaNext = bettaNext
            this.ro = ro
            this.S = S
            this.B = B
            this.X = X
            this.sharePriceBehavior = sharePriceBehavior
        }

        override fun toString(): String {
            val price = if (sharePriceBehavior.equals(SharePriceBehavior.DROP))  "Акции упали"
            else if (sharePriceBehavior.equals(SharePriceBehavior.RISE))  "Акции поднялись" else "Начальное состояние"
            return price + ". Портфель на следующий день: (γ: "+gammaNext + ", β: "+ bettaNext+") X: "+ X
        }
    }

    var iterations = ArrayList<IterationValue>()

    val N: Int

    private val a: Double
    private val b: Double
    private val r: Double
    private val K: Double
    private val B0: Double
    private val S0: Double
    private val p: Double get(){
        return (r-a)/(b-a)
    }

    val CN: Double

    constructor(a: Double, b: Double, r: Double, K: Double, B0: Double, S0: Double, N: Int) {
        this.a = a
        this.b = b
        this.r = r
        this.K = K
        this.B0 = B0
        this.S0 = S0
        this.N = N

        val gamma = gamma_n(1, S0)
        val betta = betta_n(1, S0, B0)

        iterations.add(IterationValue(gamma, betta, 0.0, S0, B0, 0.0, SharePriceBehavior.NOT_CHANGED))

        CN = CN(S0)
    }

    private fun f (x: Double) : Double{
        return Math.max(0.0, x-K)
    }

    private fun FN (N: Int, S: Double):Double{
        var sum = 0.0
        for (k in 0..N){
            sum += binomialCoefficient(N, k) *
                    Math.pow(p, k.toDouble()) *
                    Math.pow(1-p, (N-k).toDouble())*
                    Math.max(0.0, S*Math.pow(1+a, N.toDouble())* Math.pow((1+b)/(1+a), k.toDouble()) - K)
        }

        return sum
    }

    private fun Fn (n: Int, x: Double): Double{
        var sum = 0.0
        for (k in 0..n){
            sum += f(x* Math.pow(1+b, k.toDouble())*Math.pow(1+a, (n-k).toDouble()))*
                    binomialCoefficient(n, k)*Math.pow(p, k.toDouble())*Math.pow(1-p, (n-k).toDouble())
        }

        return sum
    }

    private fun CN (S0: Double): Double{
        return Math.pow(1+r, -N.toDouble()) * FN(N, S0)
    }

    private fun SN (S_prev:Double , ro_n: Double): Double{
        return (1+ro_n)*S_prev
    }

    private fun BN (B_prev: Double): Double{
        return (1+r)*B_prev
    }

    private fun gamma_n (n: Int, S_prev: Double): Double{
        return Math.pow(1+r, (-N+n).toDouble())*
                (Fn(N-n, S_prev*(1+b)) - Fn(N-n, S_prev*(1+a)))/(S_prev*(b-a))
    }

    private fun betta_n (n: Int, S_prev: Double, B_prev: Double): Double{
        return Fn(N-n+1, S_prev)/BN(B_prev) - Math.pow(1+r, (-(N-n)).toDouble())*
                (Fn(N-n, S_prev*(1+b)) - Fn(N-n, S_prev*(1+a))) / (B_prev*(b-a))
    }

    private fun binomialCoefficient(n: Int, k: Int): Long{
        var k = k
        if (k > n-k){
            k = n-k
        }
        var b: Long = 1

        var m = n
        for (i in 1..k){
            b = b*m / i
            m--
        }
        return b
    }

    fun nextStep (priceBehaviour: SharePriceBehavior){
        val prevIteration = iterations.last()
        val prevS = prevIteration.S
        val prevB = prevIteration.B

        val n = iterations.count()

        val ro = if (priceBehaviour.equals(SharePriceBehavior.DROP)) a
                else if (priceBehaviour.equals(SharePriceBehavior.RISE)) b
                else 1.0
        var currentS = SN(prevS,ro)
        var currentB = BN(prevB)
        val gamma = gamma_n(n+1, currentS)
        val betta = betta_n(n+1, currentS, currentB)

        val X = prevIteration.bettaNext * currentB + prevIteration.gammaNext * currentS

        iterations.add(IterationValue(gamma, betta, ro, currentS, currentB, X, priceBehaviour))
    }
}

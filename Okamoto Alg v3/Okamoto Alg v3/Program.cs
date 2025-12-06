using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;

public class OkamotoUchiyamaCryptosystem
{
    public const string Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ?!";

    public BigInteger p;
    public BigInteger q;
    public BigInteger g; // Generator
    public BigInteger x; // Private key
    public BigInteger h; // Public key
    public BigInteger n;

    public OkamotoUchiyamaCryptosystem()
    {
        p = GenerateRandomPrime();
        q = GenerateRandomPrime();
        //g = BigInteger.Parse("191");
        n = p * p * q;

        do
        {
            g = GenerateRandomG();
        } while (BigInteger.GreatestCommonDivisor(g, n) != BigInteger.One || BigInteger.ModPow(g, p - 1, p * p) == BigInteger.One || BigInteger.ModPow(g, p * (p - 1), p * p) != BigInteger.One);

        x = GeneratePrivateKey();
        h = BigInteger.ModPow(g, n, n);
    }

    private BigInteger GenerateRandomPrime()
    {
        Random random = new Random();
        BigInteger prime;
        bool isPrime;
        do
        {
            prime = BigIntegerExtensions.NextBigInteger(random, BigInteger.Pow(10, 10), BigInteger.Pow(10, 11));
            isPrime = IsPrime(prime);
        } while (!isPrime);

        return prime;
    }

    private bool IsPrime(BigInteger number)
    {
        if (number <= 1)
            return false;

        if (number == 2 || number == 3)
            return true;

        if (number % 2 == 0 || number % 3 == 0)
            return false;

        BigInteger sqrt = (BigInteger)Math.Sqrt((double)number);
        for (BigInteger i = 5; i <= sqrt; i += 6)
        {
            if (number % i == 0 || number % (i + 2) == 0)
                return false;
        }

        return true;
    }

    private BigInteger GenerateRandomG()
    {
        Random random = new Random();
        BigInteger g;
        do
        {
            g = BigIntegerExtensions.NextBigInteger(random, BigInteger.One, n - BigInteger.One);
        } while (BigInteger.GreatestCommonDivisor(g, n) != BigInteger.One);

        return g;
    }

    private BigInteger GeneratePrivateKey()
    {
        Random random = new Random();
        BigInteger privateKey;
        do
        {
            privateKey = BigIntegerExtensions.NextBigInteger(random, BigInteger.One, p - BigInteger.One);
        } while (BigInteger.GreatestCommonDivisor(privateKey, p - BigInteger.One) != BigInteger.One);

        return privateKey;
    }

    public string Encrypt(string message)
    {
        message = message.ToUpper();
        List<BigInteger> cipherText = new List<BigInteger>();

        foreach (char c in message)
        {
            int index = Alphabet.IndexOf(c);
            if (index >= 0)
            {
                BigInteger encryptedValue = EncryptIndex(index);
                cipherText.Add(encryptedValue);
            }
        }

        return string.Join(" ", cipherText);
    }

    public string Decrypt(string cipherText)
    {
        List<BigInteger> encryptedValues = cipherText.Split(' ')
                                                     .Select(BigInteger.Parse)
                                                     .ToList();

        string decryptedMessage = "";

        foreach (BigInteger value in encryptedValues)
        {
             BigInteger decryptedValue = DecryptValue(value);
             char decryptedChar = Alphabet[(int)decryptedValue];
             decryptedMessage += decryptedChar;
        }

        return decryptedMessage;
    }


    private BigInteger EncryptIndex(int index)
    {
        BigInteger r = GenerateRandomR();

        BigInteger gPowerIndex = BigInteger.ModPow(g, index, n);
        BigInteger hPowerR = BigInteger.ModPow(h, r, n);

        BigInteger cipherValue = (gPowerIndex * hPowerR) % n;

        return cipherValue;
    }

    private BigInteger DecryptValue(BigInteger value)
    {
        BigInteger a = (BigInteger.ModPow(value, p - 1, p * p) - 1) / p;
        BigInteger b = (BigInteger.ModPow(g, p - 1, p * p) - 1) / p;
        BigInteger bInverse = ModInverse(b, p);

        BigInteger decryptedValue = (a * bInverse) % p;

        return decryptedValue;
    }

    private BigInteger ModInverse(BigInteger a, BigInteger m)
    {
        BigInteger m0 = m;
        BigInteger y = 0;
        BigInteger x = 1;

        if (m == 1)
            return 0;

        while (a > 1)
        {
            BigInteger q = a / m;
            BigInteger t = m;

            m = a % m;
            a = t;
            t = y;

            y = x - q * y;
            x = t;
        }

        if (x < 0)
            x += m0;

        return x;
    }

    private BigInteger GenerateRandomR()
    {
        Random random = new Random();
        BigInteger r;
        do
        {
            r = BigIntegerExtensions.NextBigInteger(random, BigInteger.One, n - BigInteger.One);
        } while (BigInteger.GreatestCommonDivisor(r, n) != BigInteger.One);

        return r;
    }
}

public static class BigIntegerExtensions
{
    public static BigInteger NextBigInteger(this Random random, BigInteger minValue, BigInteger maxValue)
    {
        byte[] bytes = maxValue.ToByteArray();
        BigInteger result;
        random.NextBytes(bytes);
        bytes[bytes.Length - 1] &= 0x7F;
        result = new BigInteger(bytes);
        if (result < minValue || result >= maxValue)
        {
            return NextBigInteger(random, minValue, maxValue);
        }
        return result;
    }
}

public class Program
{
    public static void Main(string[] args)
    {
        OkamotoUchiyamaCryptosystem cryptosystem = new OkamotoUchiyamaCryptosystem();

        Console.WriteLine("Alphabet: " + OkamotoUchiyamaCryptosystem.Alphabet.ToString());

        Console.WriteLine("Private Key (p, q): " + cryptosystem.p + ", " + cryptosystem.q);
        Console.WriteLine("Public Key (n, g, h): " + cryptosystem.n + ", " + cryptosystem.g + ", " + cryptosystem.h);

        string message = "WHOA COOL?";
        Console.WriteLine("Original message: " + message);

        string cipherText = cryptosystem.Encrypt(message);
        Console.WriteLine("Encrypted message: " + cipherText);

        string decryptedMessage = cryptosystem.Decrypt(cipherText);
        Console.WriteLine("Decrypted message: " + decryptedMessage);

        bool isCorrect = decryptedMessage == message;
        Console.WriteLine("Decryption is correct: " + isCorrect);
    }
}

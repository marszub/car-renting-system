using Auth.Security;
using test.Utils;

namespace test
{
    public class HashGeneratorTest
    {
        private static readonly string alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz#@$^*()'\"/\\-+=";

        private static IEnumerable<object[]> GetPairsOfDifferent(int maxLength, int numTests, int seed)
        {
            Random random = new Random(seed);
            var data = new List<object[]>();

            for (int i = 0; i < numTests; i++)
            {
                var first = Generators.NextStrings(random, alphabet, (1, maxLength), 1).First();
                var second = Generators.NextStrings(random, alphabet, (1, maxLength), 1).First();
                if (first == second)
                {
                    i--;
                    continue;
                }

                data.Add(new object[] { first, second });
            }

            return data;
        }

        private static IEnumerable<object[]> GetStrings(int maxLength, int numTests, int seed) =>
            Generators.NextStrings(
                new Random(seed),
                alphabet,
                (1, maxLength),
                numTests)
            .Select(str => new object[] {str});

        [Theory]
        [MemberData(nameof(GetStrings), parameters: new object[] { 100, 10, 509389 })]
        public void HashIsDifferentFromOriginal(string plainText)
        {
            Assert.NotEqual(plainText, HashGenerator.GetHash(plainText));
        }

        [Theory]
        [MemberData(nameof(GetStrings), parameters: new object[] { 100, 10, 50328934 })]
        public void SameStringHasSameHash(string plainText)
        {
            Assert.Equal(HashGenerator.GetHash(plainText), HashGenerator.GetHash(plainText));
        }

        [Theory]
        [MemberData(nameof(GetPairsOfDifferent), parameters: new object[] {100, 10, 503289})]
        public void DifferentStringHasDifferentHash(string plainText1, string plainText2)
        {
            Assert.NotEqual(HashGenerator.GetHash(plainText1), HashGenerator.GetHash(plainText2));
        }

        [Theory]
        [MemberData(nameof(GetStrings), parameters: new object[] { 100, 20, 5093289 })]
        public void VerifyHash(string plainText)
        {
            Assert.True(HashGenerator.VerifyHash(plainText, HashGenerator.GetHash(plainText)));
        }

        [Theory]
        [MemberData(nameof(GetStrings), parameters: new object[] { 10000, 5, 50989 })]
        public void HashLongString(string plainText)
        {
            Assert.True(HashGenerator.VerifyHash(plainText, HashGenerator.GetHash(plainText)));
        }
    }
}

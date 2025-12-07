namespace Day4
{
    class Solution
    {
        static int CountNeighbors(string[] map, int r, int c)
        {
            int mapHeight = map.Length;
            int mapWidth = map[0].Length;
            int count = 0;
            for (int row_i = Math.Max(0, r - 1); row_i <= Math.Min(mapHeight - 1, r + 1); row_i++)
            {
                for (int col_i = Math.Max(0, c - 1); col_i <= Math.Min(mapWidth - 1, c + 1); col_i++)
                {
                    if ((row_i != r || col_i != c) && map[row_i][col_i] == '@')
                    {
                        count++;
                    }
                }
            }
            return count;
        }
        static int CountAccessible(string[] map)
        {
            int accessibleCount = 0;
            for (int i = 0; i < map.Length; i++)
            {
                for (int j = 0; j < map[i].Length; j++)
                {
                    if (map[i][j] == '@' && CountNeighbors(map, i, j) < 4)
                    {
                        accessibleCount++;
                    }
                }
            }
            return accessibleCount;
        }
        static void Main(string[] args)
        {
            string inputFilePath = "input.txt";
            var map = File.ReadAllLines(inputFilePath);

            Console.WriteLine($"There are {CountAccessible(map)} rolls of paper that can be accessed by a forklift ");
        }
    }

}

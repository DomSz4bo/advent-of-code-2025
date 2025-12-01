import Data.List

decode :: String -> Int
decode (dir:x)
    | dir == 'L' = - read x
    | otherwise = read x

-- (newPos, numOfZerosMet)
dial :: Int -> Int -> (Int, Int)
dial pos move = (newPos, zerosMet)
    where
        unPos = pos + move
        newPos = mod unPos 100
        zerosMet
            | unPos == 0 = 1
            | unPos > 0 = div unPos 100
            | pos > 0 = 1 + div (abs unPos) 100
            | otherwise = div (abs unPos) 100

zeroPositions :: [Int] -> Int
zeroPositions moves = snd (foldl hf (50, 0) moves)
    where
        hf (p,c) m = (newPos, c + zeros)
            where
                (newPos, zeros) = dial p m

main :: IO()
main = do
    s <- readFile "input.txt"
    let moves = map decode (lines s)
    print (zeroPositions moves)

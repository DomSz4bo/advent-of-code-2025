import Data.List

decode :: String -> Int
decode (dir:x) 
    | dir == 'L' = - read x
    | otherwise = read x

dial :: Int -> Int -> Int
dial pos move
    | 0 <= newPos && newPos <= 99 = newPos 
    | otherwise = mod newPos 100
    where newPos = pos + move

zeroPositions :: [Int] -> Int
zeroPositions moves = snd (foldl hf (50, 0) moves)
    where
        hf (p,c) m = (dial p m, if dial p m == 0 then c+1 else c)

main :: IO()
main = do
    s <- readFile "input.txt"
    let moves = map decode (lines s)
    print (zeroPositions moves)

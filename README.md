# betterhorses

A Minecraft mod that replaces the horse breeding algorithm. The default
algorithm takes the stats from both parents and a 3rd random horse and combines
them and then divides by 3. This has the somewhat realistic effect of making is
exponentially harder to breed better horses, but it's quite awful from a
gameplay perspective.

This mod instead creates the child horse with stats averaged from the parents
and then applies linear variation to each stat and finally clamps it between the
min/max values in vanilla Minecraft. It's still quite a feat to get a "perfect"
horse, but at least now it's a goal you can work towards.

## setup and build
- Clone this repo.
- Run `./gradlew build` to build (result in `build/libs/`)

## contributing

Discussion and patches can be found [here](https://lists.sr.ht/~kota/public-inbox).

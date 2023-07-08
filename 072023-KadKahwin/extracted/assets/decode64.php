<?php

$f = fopen("main.lua", "rb");
$s = stream_get_contents($f);
fclose($f);

$o = fopen("main.lua.d", "w+");
fwrite($o, base64_decode($s));
fclose($o);
<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import barImg from './assets/bar.jpg'

const route = useRoute()
// Le fond photo n'apparaît que sur la partie client (pas sur /admin).
const estClient = computed(() => !route.path.startsWith('/admin'))
</script>

<template>
  <div
    v-if="estClient"
    class="fond-bar"
    :style="{ backgroundImage: `url(${barImg})` }"
    aria-hidden="true"
  ></div>
  <router-view />
</template>

<style>
.fond-bar {
  position: fixed;
  inset: 0;
  z-index: -1;
  background-size: cover;
  background-position: center;
  /* flou + assombrissement pour rester lisible derrière le contenu en verre */
  filter: blur(20px) brightness(0.5) saturate(1.1);
  transform: scale(1.12); /* masque les bords nets créés par le flou */
  opacity: 0.6;
  pointer-events: none;
}
</style>

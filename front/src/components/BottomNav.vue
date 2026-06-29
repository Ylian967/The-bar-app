<script setup lang="ts">
import { usePanierStore } from '../stores/panier'
const panier = usePanierStore()
</script>

<template>
  <nav class="bnav">
    <RouterLink to="/" class="b" active-class="on">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M3 10.5 12 3l9 7.5" /><path d="M5 9.5V20h14V9.5" /></svg>
      Accueil
    </RouterLink>
    <RouterLink to="/panier" class="b" active-class="on">
      <span class="ic">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="9" cy="20" r="1.4" /><circle cx="17" cy="20" r="1.4" /><path d="M3 4h2l2.2 11h10l2-8H6.5" /></svg>
        <em v-if="panier.nbArticles > 0">{{ panier.nbArticles }}</em>
      </span>
      Panier
    </RouterLink>
    <RouterLink to="/mes-commandes" class="b" active-class="on">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M6 3h12v18l-2.5-1.6L13 20.4 10.5 19 8 20.4 6 21z" /><path d="M9 8h6M9 12h5" /></svg>
      Commandes
    </RouterLink>
  </nav>
</template>

<style scoped>
.bnav {
  position: fixed;
  bottom: 16px;
  left: 50%;
  transform: translateX(-50%);
  width: calc(100% - 36px);
  max-width: 420px;
  display: flex;
  justify-content: space-around;
  background: #fff;
  padding: 13px 8px;
  box-shadow: 0 14px 34px rgba(36, 16, 70, 0.2);
  border-radius: 24px;
}
.b {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  font-size: 11px;
  font-weight: 600;
  color: #b6adc6;
}
.b svg { width: 22px; height: 22px; }
.b.on { color: var(--coral); }
.ic { position: relative; }
.ic em {
  position: absolute;
  top: -6px;
  right: -8px;
  background: var(--coral);
  color: #fff;
  font-style: normal;
  font-size: 10px;
  font-weight: 700;
  min-width: 16px;
  height: 16px;
  border-radius: 999px;
  display: grid;
  place-items: center;
  padding: 0 3px;
}
</style>

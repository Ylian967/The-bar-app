<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { catalogueService } from '../../services/catalogue'
import { imageUrl } from '../../services/http'
import { usePanierStore } from '../../stores/panier'
import type { Cocktail, Taille } from '../../types/models'

const route = useRoute()
const router = useRouter()
const panier = usePanierStore()

const cocktail = ref<Cocktail | null>(null)
const tailleChoisie = ref<Taille | null>(null)
const quantite = ref(1)

const prixChoisi = computed(() => {
  const t = cocktail.value?.tailles.find((x) => x.taille === tailleChoisie.value)
  return t ? t.prix : 0
})

onMounted(async () => {
  const id = Number(route.params.id)
  cocktail.value = await catalogueService.trouverCocktail(id)
  const tailles = cocktail.value.tailles
  tailleChoisie.value = (tailles.find((t) => t.taille === 'M') ?? tailles[0])?.taille ?? null
})

function ajouterAuPanier() {
  if (!cocktail.value || !tailleChoisie.value) return
  panier.ajouter(cocktail.value, tailleChoisie.value, prixChoisi.value, quantite.value)
  router.push({ name: 'panier' })
}
</script>

<template>
  <div v-if="cocktail" class="screen">
    <div class="hero">
      <img :src="imageUrl(cocktail.imageUrl)" :alt="cocktail.nom" />
      <div class="ov"></div>
      <button class="rond back" @click="router.back()">‹</button>
    </div>

    <div class="corps">
      <span class="tag">{{ cocktail.categorie }}</span>
      <h1>{{ cocktail.nom }}</h1>
      <p class="desc">{{ cocktail.description }}</p>

      <h3>Ingrédients</h3>
      <div class="ings">
        <span v-for="ing in cocktail.ingredients" :key="ing" class="ing">{{ ing }}</span>
      </div>

      <h3>Taille</h3>
      <div class="tailles">
        <button
          v-for="t in cocktail.tailles"
          :key="t.taille"
          class="sz"
          :class="{ on: tailleChoisie === t.taille }"
          @click="tailleChoisie = t.taille"
        >
          <span class="l">{{ t.taille }}</span>
          <span class="p">{{ t.prix }} €</span>
        </button>
      </div>

      <div class="bas">
        <div class="qty">
          <button @click="quantite > 1 && quantite--">−</button>
          <span>{{ quantite }}</span>
          <button @click="quantite++">+</button>
        </div>
        <button class="btn" @click="ajouterAuPanier">
          Ajouter au panier · {{ (prixChoisi * quantite).toFixed(2) }} €
        </button>
      </div>
    </div>
  </div>
  <p v-else class="etat">Chargement…</p>
</template>

<style scoped>
.screen { max-width: 480px; margin: 0 auto; min-height: 100vh; background: var(--cream); }
.hero { position: relative; height: 280px; }
.hero img { width: 100%; height: 100%; object-fit: cover; }
.hero .ov { position: absolute; inset: 0; background: linear-gradient(180deg, rgba(20, 8, 40, 0.35), transparent 40%); }
.rond {
  position: absolute; top: 20px; width: 42px; height: 42px; border-radius: 50%;
  background: rgba(255, 255, 255, 0.92); color: var(--ink); font-size: 22px; line-height: 1;
}
.back { left: 18px; }
.corps { padding: 22px 18px 30px; }
.tag { display: inline-block; background: #ffe4ea; color: var(--coral-d); font-weight: 600; font-size: 12px; padding: 5px 12px; border-radius: 999px; }
.corps h1 { font-size: 28px; font-weight: 800; margin: 10px 0 6px; }
.desc { color: var(--ink-soft); font-size: 14px; line-height: 1.5; }
.corps h3 { font-size: 16px; margin: 22px 0 10px; }
.ings { display: flex; flex-wrap: wrap; gap: 8px; }
.ing { background: #fff; border: 1px solid var(--line); border-radius: 999px; padding: 7px 13px; font-size: 12px; font-weight: 500; }
.tailles { display: flex; gap: 10px; }
.sz { flex: 1; background: #fff; border: 1.5px solid var(--line); border-radius: 16px; padding: 12px 0; display: flex; flex-direction: column; align-items: center; gap: 2px; }
.sz .l { font-family: 'Poppins', sans-serif; font-weight: 700; font-size: 16px; }
.sz .p { font-size: 12px; color: var(--ink-soft); }
.sz.on { border-color: transparent; background: linear-gradient(135deg, var(--coral), var(--mango)); color: #fff; }
.sz.on .p { color: #fff; }
.bas { display: flex; gap: 14px; align-items: center; margin-top: 26px; }
.qty { display: flex; align-items: center; gap: 12px; background: #fff; border: 1px solid var(--line); border-radius: 16px; padding: 10px 14px; font-family: 'Poppins', sans-serif; font-weight: 700; }
.qty button { width: 30px; height: 30px; border-radius: 10px; background: #ffe4ea; color: var(--coral); font-size: 18px; }
.btn { flex: 1; }
</style>

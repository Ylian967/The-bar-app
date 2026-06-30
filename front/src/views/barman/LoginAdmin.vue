<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'

const router = useRouter()
const auth = useAuthStore()

const email = ref('barmaker@barapp.fr')
const motDePasse = ref('')
const erreur = ref('')
const envoi = ref(false)

async function seConnecter() {
  erreur.value = ''
  envoi.value = true
  try {
    await auth.login(email.value, motDePasse.value)
    router.push({ name: 'commandes-a-traiter' })
  } catch {
    erreur.value = 'Email ou mot de passe incorrect'
  } finally {
    envoi.value = false
  }
}
</script>

<template>
  <div class="login">
    <form class="lcard" @submit.prevent="seConnecter">
      <div class="lg">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
          <path d="M4 4h16l-8 9z" /><path d="M12 13v6" /><path d="M8 21h8" />
        </svg>
      </div>
      <h2>Le Bar'app</h2>
      <p class="sub">Espace Barmaker</p>

      <label class="field">Email
        <input v-model="email" type="email" placeholder="barmaker@barapp.fr" required />
      </label>
      <label class="field">Mot de passe
        <input v-model="motDePasse" type="password" placeholder="••••••••" required />
      </label>

      <p v-if="erreur" class="erreur">{{ erreur }}</p>
      <button class="btn" type="submit" :disabled="envoi">{{ envoi ? 'Connexion…' : 'Se connecter' }}</button>
    </form>
  </div>
</template>

<style scoped>
.login {
  min-height: 100vh;
  display: grid;
  place-items: center;
  background:
    radial-gradient(900px 500px at 80% -10%, rgba(255, 77, 109, 0.18), transparent),
    radial-gradient(700px 500px at 0% 110%, rgba(0, 194, 168, 0.18), transparent), var(--cream);
  padding: 20px;
}
.lcard { width: 360px; max-width: 100%; background: var(--glass-strong); border: 1px solid var(--stroke); backdrop-filter: blur(20px) saturate(150%); border-radius: 26px; padding: 34px 30px; box-shadow: var(--shadow); text-align: center; }
.lg { width: 64px; height: 64px; border-radius: 20px; margin: 0 auto; background: var(--accent); display: grid; place-items: center; }
.lg svg { width: 36px; height: 36px; color: #fff; }
h2 { font-size: 24px; font-weight: 800; margin-top: 12px; }
.sub { color: var(--ink-soft); font-size: 13px; margin-bottom: 22px; }
.field { display: block; text-align: left; font-size: 12px; font-weight: 600; color: var(--ink-soft); margin-bottom: 14px; }
.field input { display: block; width: 100%; margin-top: 6px; background: var(--cream); border: 1.5px solid var(--line); border-radius: 14px; padding: 13px 14px; font-size: 14px; font-family: inherit; }
.erreur { color: var(--coral-d); font-size: 13px; margin-bottom: 12px; }
.btn { margin-top: 4px; }
</style>
